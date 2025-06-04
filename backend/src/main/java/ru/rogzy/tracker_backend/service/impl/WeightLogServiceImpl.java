package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.WeightLogForDayResponseDTO;
import ru.rogzy.tracker_backend.converters.WeightLogMapper;
import ru.rogzy.tracker_backend.repository.WeightLogRepository;
import ru.rogzy.tracker_backend.repository.models.WeightLogDO;
import ru.rogzy.tracker_backend.service.WeightLogService;
import ru.rogzy.tracker_backend.controller.models.WeightLogDTO;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeightLogServiceImpl implements WeightLogService {
    private final WeightLogRepository repository;
    private WeightLogMapper weightLogMapper;

    @Override
    public WeightLogDTO create(Long userId, WeightLogDTO dto) {
        var log = new WeightLogDO();
        log.setCreatedAt(Instant.now());
        log.setUpdatedAt(Instant.now());
        log.setUserId(userId);
        log.setWeight(dto.getWeight());
        var save = repository.save(log);
        return weightLogMapper.doTODto(save);
    }

    @Override
    public WeightLogForDayResponseDTO getDay(Long userId, Instant from, Instant to) {
        var logs = repository.findAllByPeriod(userId, from, to);

        if (logs.size() >= 2) {
            var last = logs.getLast();
            var previous = logs.get(logs.size() - 2);
            return new WeightLogForDayResponseDTO(last.getId(), last.getWeight(), previous.getId(), previous.getWeight());
        }

        if (logs.size() == 1) {
            var last = logs.getFirst();
            var previous = repository.findLatestBefore(userId, from);
            return new WeightLogForDayResponseDTO(last.getId(), last.getWeight(), previous.stream().findFirst().map(WeightLogDO::getId).orElse(null), previous.stream().findFirst().map(WeightLogDO::getWeight).orElse(null));
        }

        var previous = repository.findLatestBefore(userId, from);
        return new WeightLogForDayResponseDTO(null, null, previous.stream().findFirst().map(WeightLogDO::getId).orElse(null), previous.stream().findFirst().map(WeightLogDO::getWeight).orElse(null));
    }

    @Override
    public void deleteByUserIdAndId(Long userId, Long id) {
        repository.deleteByUserIdAndId(userId, id);
    }
}
