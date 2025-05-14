package ru.rogzy.tracker_backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.rogzy.tracker_backend.repository.UserAuthorizationRepository;
import ru.rogzy.tracker_backend.repository.models.UserDO;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!prod")
public class TestDataInserterConfig implements ApplicationRunner {
    private final UserAuthorizationRepository userAuthorizationRepository;
    @Value("${TEST_USER_PASSWORD_HASH:}")
    private String testUserPasswordHash;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (testUserPasswordHash != null && !testUserPasswordHash.isEmpty()) {
            var testUser = UserDO.builder()
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .email("test@test.ru")
                    .passwordHash(testUserPasswordHash)
                    .verified(true)
                    .build();
            userAuthorizationRepository.save(testUser);
            log.info("Test user was added: test@test.ru");
        }
    }
}
