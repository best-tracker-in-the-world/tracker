package ru.rogzy.tracker_backend.scheduler.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rogzy.tracker_backend.repository.*;
import ru.rogzy.tracker_backend.scheduler.DeleteAccounts;

import java.time.Instant;

@Slf4j
@Component
@AllArgsConstructor
public class DeleteAccountsImpl implements DeleteAccounts {
    private UserAuthorizationRepository userAuthorizationRepository;
    private FoodLogRepository foodLogRepository;
    private FavoriteFoodRepository favoriteFoodRepository;
    private GoalLogRepository goalLogRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private WeightLogRepository weightLogRepository;

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void deleteAccounts() {
        log.info("Delete accounts started");
        var allByDeletedAtBefore = userAuthorizationRepository.findAllByDeletedAtBefore(Instant.now());
        log.info("Should be deleted {} accounts", allByDeletedAtBefore.size());
        foodLogRepository.deleteAllByUserIds(allByDeletedAtBefore);
        favoriteFoodRepository.deleteAllByUserIds(allByDeletedAtBefore);
        goalLogRepository.deleteAllByUserIds(allByDeletedAtBefore);
        verificationTokenRepository.deleteAllByUserIds(allByDeletedAtBefore);
        weightLogRepository.deleteAllByUserIds(allByDeletedAtBefore);
        log.info("Delete accounts finished");
    }
}
