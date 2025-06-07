package ru.rogzy.tracker_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rogzy.tracker_backend.controller.models.*;
import ru.rogzy.tracker_backend.exception.UserNotFoundException;
import ru.rogzy.tracker_backend.repository.UserAuthorizationRepository;
import ru.rogzy.tracker_backend.repository.VerificationTokenRepository;
import ru.rogzy.tracker_backend.repository.models.UserDO;
import ru.rogzy.tracker_backend.repository.models.VerificationTokenDO;
import ru.rogzy.tracker_backend.security.JwtService;

import ru.rogzy.tracker_backend.service.AuthService;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAuthorizationRepository authorizationRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final JwtService jwtService;

    @Value("${app.verification.token-expiration-minutes}")
    private Long tokenExpirationMinutes;
    @Value("${app.where-am-i.url}")
    private String applicationDomain;

    public void register(RegistrationRequestDTO requestDTO) {
        authorizationRepository.findByEmail(requestDTO.getEmail())
                .ifPresent(u -> { throw new IllegalArgumentException("Email уже зарегистрирован"); });

        String hash = passwordEncoder.encode(requestDTO.getPassword());

        var user = UserDO.builder()
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
//                .login(requestDTO.getLogin())
                .email(requestDTO.getEmail())
                .passwordHash(hash)
                .verified(false)
                .build();

        authorizationRepository.save(user);
        String token = UUID.randomUUID().toString();
        var verificationTokenDO = VerificationTokenDO.builder()
                .token(token)
                .userId(user.getId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * tokenExpirationMinutes))
                .build();
        verificationTokenRepository.save(verificationTokenDO);

        String link = String.format("http://%s/v1/api/auth/confirm?token=%s", applicationDomain, token);
        emailService.sendVerification(user.getEmail(), link);
    }

    @Transactional
    public void confirmToken(String token) {
        var vtoken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Неверный токен"));

        if (vtoken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Токен истёк");
        }

        var user = authorizationRepository.findById(vtoken.getUserId())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));
        user.setVerified(true);
        authorizationRepository.save(user);
        verificationTokenRepository.deleteByToken(token);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        var user = authorizationRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Неверный email или пароль"));
        if (!user.getVerified()) {
            throw new IllegalArgumentException("Email не подтверждён");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Неверный email или пароль");
        }
        var jwt = jwtService.generateToken(user.getEmail(), user.getId());
        return LoginResponseDTO.builder()
                .accessToken(jwt)
                .build();
    }

//    public LogoutResponseDTO logout(LogoutRequestDTO requestDTO) {
//
//    }

    public Long getUserByEmail(String email) {
        var userDOOpt = authorizationRepository.findByEmail(email);
        if (userDOOpt.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userDOOpt.get().getId();
    }
}
