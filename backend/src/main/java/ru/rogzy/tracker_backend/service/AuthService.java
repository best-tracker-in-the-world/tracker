package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.LoginRequestDTO;
import ru.rogzy.tracker_backend.controller.models.LoginResponseDTO;
import ru.rogzy.tracker_backend.controller.models.RegistrationRequestDTO;

public interface AuthService {
    void register(RegistrationRequestDTO requestDTO);
    void confirmToken(String token);
    LoginResponseDTO login(LoginRequestDTO request);
    Long getUserByEmail(String email);
}
