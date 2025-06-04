package ru.rogzy.tracker_backend.service;

import ru.rogzy.tracker_backend.controller.models.GoalLogDTO;
import ru.rogzy.tracker_backend.controller.models.GoalLogForDayResponseDTO;

import java.time.Instant;

public interface GoalLogService {
    GoalLogDTO create(Long userId, GoalLogDTO dto);
    GoalLogForDayResponseDTO getDay(Long userId, Instant from, Instant to);
    void deleteByUserIdAndId(Long userId, Long id);
}
