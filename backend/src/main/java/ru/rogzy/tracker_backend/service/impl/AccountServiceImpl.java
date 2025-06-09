package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.exception.UserNotFoundException;
import ru.rogzy.tracker_backend.repository.UserAuthorizationRepository;
import ru.rogzy.tracker_backend.service.AccountService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private UserAuthorizationRepository repository;

    @Override
    public Instant delete(Long id) {
        var userByIdOpt = repository.findById(id);
        if (userByIdOpt.isEmpty()) {
            throw new UserNotFoundException();
        }
        var userDO = userByIdOpt.get();
        if (userDO.getDeletedAt() == null) {
            var timeForDelete = Instant.now().plus(3, ChronoUnit.MONTHS);
            userDO.setDeletedAt(timeForDelete);
            repository.save(userDO);
            return timeForDelete;
        } else {
            return userDO.getDeletedAt();
        }
    }

    @Override
    public void restore(Long id) {
        var userByIdOpt = repository.findById(id);
        if (userByIdOpt.isEmpty()) {
            throw new UserNotFoundException();
        }
        var userDO = userByIdOpt.get();
        if (userDO.getDeletedAt() != null) {
            userDO.setDeletedAt(null);
            repository.save(userDO);
        }
    }
}
