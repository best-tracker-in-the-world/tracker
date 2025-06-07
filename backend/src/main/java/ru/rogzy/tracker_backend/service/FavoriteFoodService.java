package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

import java.util.List;
import java.util.Optional;

public interface FavoriteFoodService {
    FavoriteFoodDTO createOrUpdate(Long userId, FavoriteFoodDTO dto);
    Optional<FavoriteFoodDTO> findByUserIdAndId(Long userId, Long id);
    List<FavoriteFoodDTO> findAll();
    void deleteByUserIdAndId(Long userId, Long id);
}