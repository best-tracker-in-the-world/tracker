package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

import java.util.List;
import java.util.Optional;

public interface FoodLogService {
    FoodLogDO create(FoodLogDTO dto);
    Optional<FoodLogDO> findById(Long id);
    List<FoodLogDO> findAll();
    FoodLogDO update(Long id, FoodLogDTO dto);
    void delete(Long id);
}