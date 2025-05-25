package ru.rogzy.tracker_backend.service.impl;

import org.springframework.stereotype.Service;

import ru.rogzy.tracker_backend.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    public void sendVerification(String email, String link) {
        // TODO: интеграция с почтовым провайдером
        System.out.printf("Send verification to %s: %s\n", email, link);
    }
}
