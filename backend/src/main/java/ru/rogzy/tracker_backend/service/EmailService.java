package ru.rogzy.tracker_backend.service;

public interface EmailService {
    void sendVerification(String email, String link);
}
