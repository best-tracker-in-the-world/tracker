package ru.rogzy.tracker_backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendVerification(String email, String link) {
        // TODO: интеграция с почтовым провайдером
        System.out.printf("Send verification to %s: %s\n", email, link);
    }
}
