package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.converters.FoodLogMapper;
import ru.rogzy.tracker_backend.repository.FoodLogRepository;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;
import ru.rogzy.tracker_backend.service.FoodLogService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodLogServiceImpl implements FoodLogService {
    private final FoodLogRepository repository;
    private FoodLogMapper foodLogMapper;

    @Override
    public FoodLogDTO createOrUpdate(Long userId, FoodLogDTO dto) {
        var logDOOpt = repository.findByUserIdAndId(userId, dto.getId());
        var log = new FoodLogDO();
        if (logDOOpt.isPresent()) {
            log = logDOOpt.get();
        } else {
            log.setCreatedAt(Instant.now());
            log.setUserId(userId);
            log.setWeight(dto.getWeight() != null ? dto.getWeight() : 100.0F);
        }
        log.setUpdatedAt(Instant.now());
        log.setName(dto.getName());
        log.setProt(dto.getProt());
        log.setCarb(dto.getCarb());
        log.setFat(dto.getFat());
        log.setCalorage(dto.getCalorage());

        var save = repository.save(log);
        return foodLogMapper.doTODto(save);
    }

    @Override
    public Optional<FoodLogDO> findByUserIdAndId(Long userId, Long id) {
        return repository.findByUserIdAndId(userId, id);
    }

    @Override
    public List<FoodLogDTO> findAllByPeriod(Long userId, Instant from, Instant to) {
        var allByPeriod = repository.findAllByPeriod(userId, from, to);
        return allByPeriod.stream().map(foodLogMapper::doTODto).collect(Collectors.toList());
    }

    @Override
    public void deleteByUserIdAndId(Long userId, Long id) {
        repository.deleteByUserIdAndId(userId, id);
    }

}

