package ru.rogzy.tracker_backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.*;
import ru.rogzy.tracker_backend.service.AuthService;

@RestController
@RequestMapping("/v1/api/auth")
@Validated
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        try {
            authService.register(requestDTO);
            return ResponseEntity.ok("Регистрация прошла успешно. Проверьте почту для подтверждения");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam String token) {
        try {
            authService.confirmToken(token);
            return ResponseEntity.ok("Email подтверждён");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        var loginResponse = authService.login(requestDTO);
        return ResponseEntity.ok().body(loginResponse);
    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<LogoutResponseDTO> logout(@Valid @RequestBody LogoutRequestDTO requestDTO) {
//        var logoutResponse = authService.logout(requestDTO);
//        return ResponseEntity.ok().body(logoutResponse);
//    }
}
