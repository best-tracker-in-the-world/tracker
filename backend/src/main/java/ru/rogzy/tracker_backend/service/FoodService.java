package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FoodRequestDTO;
import ru.rogzy.tracker_backend.repository.models.FoodDO;

import java.util.List;
import java.util.Optional;

public interface FoodService {
    FoodDO create(FoodRequestDTO dto);
    Optional<FoodDO> findById(Long id);
    List<FoodDO> findAll();
    FoodDO update(Long id, FoodRequestDTO dto);
    void delete(Long id);
}
