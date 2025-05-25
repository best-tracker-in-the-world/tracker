package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

import java.util.List;
import java.util.Optional;

public interface FavoriteFoodService {
    FavoriteFoodDO create(FavoriteFoodDTO dto);
    Optional<FavoriteFoodDO> findById(Long id);
    List<FavoriteFoodDO> findAll();
    FavoriteFoodDO update(Long id, FavoriteFoodDTO dto);
    void delete(Long id);
}