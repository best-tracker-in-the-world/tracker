package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.converters.FoodLogMapper;
import ru.rogzy.tracker_backend.repository.FoodLogRepository;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;
import ru.rogzy.tracker_backend.service.FoodLogService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodLogServiceImpl implements FoodLogService {
    private final FoodLogRepository repository;
    private FoodLogMapper foodLogMapper;

    @Override
    public FoodLogDTO create(FoodLogDTO dto) {
        var log = new FoodLogDO();
        log.setCreatedAt(Instant.now());
        log.setUpdatedAt(Instant.now());
        log.setUserId(dto.getUserId());
        log.setName(dto.getName());
        log.setProt(dto.getProt());
        log.setCarb(dto.getCarb());
        log.setFat(dto.getFat());
        log.setCalorage(dto.getCalorage());
        var save = repository.save(log);
        return foodLogMapper.doTODto(save);
    }

    @Override
    public Optional<FoodLogDO> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FoodLogDO> findAll() {
        var list = new ArrayList<FoodLogDO>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public FoodLogDO update(Long id, FoodLogDTO dto) {
        FoodLogDO log = repository.findById(id).orElseThrow();
        log.setUpdatedAt(Instant.now());
        log.setUserId(dto.getUserId());
        log.setName(dto.getName());
        log.setProt(dto.getProt());
        log.setCarb(dto.getCarb());
        log.setFat(dto.getFat());
        log.setCalorage(dto.getCalorage());
        return repository.save(log);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}

