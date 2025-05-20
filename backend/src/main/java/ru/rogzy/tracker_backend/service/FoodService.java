package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FoodDTO;
import ru.rogzy.tracker_backend.repository.models.FoodDO;

import java.util.List;
import java.util.Optional;

public interface FoodService {
    FoodDO create(FoodDTO dto);
    Optional<FoodDO> findById(Long id);
    List<FoodDO> findAll();
    FoodDO update(Long id, FoodDTO dto);
    void delete(Long id);
}
