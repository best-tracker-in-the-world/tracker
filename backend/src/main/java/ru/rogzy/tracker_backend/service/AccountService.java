package ru.rogzy.tracker_backend.service;

import java.time.Instant;

public interface AccountService {

    Instant delete(Long id);
    void restore(Long id);
}
