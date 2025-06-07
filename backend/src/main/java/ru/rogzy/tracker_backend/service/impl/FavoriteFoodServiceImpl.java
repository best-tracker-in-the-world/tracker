package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.converters.FavoriteFoodMapper;
import ru.rogzy.tracker_backend.repository.FavoriteFoodRepository;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;
import ru.rogzy.tracker_backend.service.FavoriteFoodService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteFoodServiceImpl implements FavoriteFoodService {
    private final FavoriteFoodRepository repository;
    private final FavoriteFoodMapper mapper;

    @Override
    public FavoriteFoodDTO createOrUpdate(Long userId, FavoriteFoodDTO dto) {
        var favDOOpt = repository.findByUserIdAndId(userId, dto.getId());
        var fav = new FavoriteFoodDO();
        if (favDOOpt.isPresent()) {
            fav = favDOOpt.get();
        } else {
            fav.setCreatedAt(Instant.now());
            fav.setUserId(userId);
        }
        fav.setUpdatedAt(Instant.now());
        fav.setFoodId(dto.getFoodId());
        fav.setName(dto.getName());
        fav.setOverrideProt(dto.getOverrideProt());
        fav.setOverrideCarb(dto.getOverrideCarb());
        fav.setOverrideFat(dto.getOverrideFat());
        fav.setOverrideCalorage(dto.getOverrideCalorage());
        var result = repository.save(fav);
        return mapper.doTODto(result);
    }

    @Override
    public Optional<FavoriteFoodDTO> findByUserIdAndId(Long userId, Long id) {
        var favOpt = repository.findByUserIdAndId(userId, id);
        return favOpt.map(mapper::doTODto);
    }

    @Override
    public List<FavoriteFoodDTO> findAll() {
        return repository.findAll().stream().map(mapper::doTODto).toList();
    }

    @Override
    public void deleteByUserIdAndId(Long userId, Long id) {
        repository.deleteByUserIdAndId(userId, id);
    }

}
