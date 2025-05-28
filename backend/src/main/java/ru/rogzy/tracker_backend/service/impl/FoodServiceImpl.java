package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FoodDTO;
import ru.rogzy.tracker_backend.repository.FoodRepository;
import ru.rogzy.tracker_backend.repository.models.FoodDO;
import ru.rogzy.tracker_backend.service.FoodService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository repository;

    @Override
    public FoodDO create(FoodDTO dto) {
        FoodDO food = new FoodDO();
        food.setCreatedAt(Instant.now());
        food.setUpdatedAt(Instant.now());
        food.setName(dto.getName());
        food.setProt(dto.getProt());
        food.setCarb(dto.getCarb());
        food.setFat(dto.getFat());
        food.setCalorage(dto.getCalorage());
        food.setDefaultWeight(dto.getDefaultWeight());
        return repository.save(food);
    }

    @Override
    public Optional<FoodDO> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FoodDO> findAll() {
        var list = new ArrayList<FoodDO>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public FoodDO update(Long id, FoodDTO dto) {
        FoodDO existing = repository.findById(id).orElseThrow();
        existing.setUpdatedAt(Instant.now());
        existing.setName(dto.getName());
        existing.setProt(dto.getProt());
        existing.setCarb(dto.getCarb());
        existing.setFat(dto.getFat());
        existing.setCalorage(dto.getCalorage());
        existing.setDefaultWeight(dto.getDefaultWeight());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
