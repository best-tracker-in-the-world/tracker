package ru.rogzy.tracker_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.repository.FoodLogRepository;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class FoodLogServiceImpl implements FoodLogService {
    private final FoodLogRepository repository;

    @Override
    public FoodLogDO create(FoodLogDTO dto) {
        FoodLogDO log = new FoodLogDO();
        log.setCreatedAt(Instant.now());
        log.setUpdatedAt(Instant.now());
        log.setUserId(dto.getUserId());
        log.setName(dto.getName());
        log.setProt(dto.getProt());
        log.setCarb(dto.getCarb());
        log.setFat(dto.getFat());
        log.setCalorage(dto.getCalorage());
        log.setWeight(dto.getWeight());
        log.setFoodId(dto.getFoodId());
        log.setFavoriteFoodId(dto.getFavoriteFoodId());
        return repository.save(log);
    }

    @Override
    public Optional<FoodLogDO> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FoodLogDO> findAll() {
        return repository.findAll();
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
        log.setWeight(dto.getWeight());
        log.setFoodId(dto.getFoodId());
        log.setFavoriteFoodId(dto.getFavoriteFoodId());
        return repository.save(log);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}

