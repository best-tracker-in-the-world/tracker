package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.GoalLogDTO;
import ru.rogzy.tracker_backend.controller.models.GoalLogForDayResponseDTO;
import ru.rogzy.tracker_backend.converters.GoalLogMapper;
import ru.rogzy.tracker_backend.repository.GoalLogRepository;
import ru.rogzy.tracker_backend.repository.models.GoalLogDO;
import ru.rogzy.tracker_backend.service.GoalLogService;

import java.time.Instant;

@Service
@AllArgsConstructor
public class GoalLogServiceImpl implements GoalLogService {
    private final GoalLogRepository repository;
    private GoalLogMapper weightLogMapper;

    @Override
    public GoalLogDTO create(Long userId, GoalLogDTO dto) {
        var log = new GoalLogDO();
        log.setCreatedAt(Instant.now());
        log.setUpdatedAt(Instant.now());
        log.setUserId(userId);
        log.setGoal(dto.getValue());
        var save = repository.save(log);
        return weightLogMapper.doTODto(save);
    }

    @Override
    public GoalLogForDayResponseDTO getDay(Long userId, Instant from, Instant to) {
        var logs = repository.findAllByPeriod(userId, from, to);

        if (logs.size() >= 2) {
            var last = logs.getLast();
            return new GoalLogForDayResponseDTO(last.getId(), last.getGoal());
        }

        if (logs.size() == 1) {
            var last = logs.getFirst();
            return new GoalLogForDayResponseDTO(last.getId(), last.getGoal());
        }

        return new GoalLogForDayResponseDTO(null, null);
    }

    @Override
    public void deleteByUserIdAndId(Long userId, Long id) {

    }
}
