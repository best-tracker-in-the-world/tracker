package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.WeightLogDTO;
import ru.rogzy.tracker_backend.controller.models.WeightLogForDayResponseDTO;

import java.time.Instant;
import java.util.List;

public interface WeightLogService {
    WeightLogDTO create(Long userId, WeightLogDTO dto);
    WeightLogForDayResponseDTO getDay(Long userId, Instant from, Instant to);
    void deleteByUserIdAndId(Long userId, Long id);
}
