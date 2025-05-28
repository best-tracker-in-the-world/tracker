package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface FoodLogService {
    FoodLogDTO createOrUpdate(Long userId, FoodLogDTO dto);
    Optional<FoodLogDO> findByUserIdAndId(Long userId, Long id);
    List<FoodLogDTO> findAllByPeriod(Long userId, Instant from, Instant requestDTOTo);
    void deleteByUserIdAndId(Long userId, Long id);
}