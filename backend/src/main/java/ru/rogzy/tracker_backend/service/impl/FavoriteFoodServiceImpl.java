package ru.rogzy.tracker_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.repository.FavoriteFoodRepository;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;
import ru.rogzy.tracker_backend.service.FavoriteFoodService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoriteFoodServiceImpl implements FavoriteFoodService {
    private final FavoriteFoodRepository repository;

    @Override
    public FavoriteFoodDO create(FavoriteFoodDTO dto) {
        FavoriteFoodDO fav = new FavoriteFoodDO();
        fav.setCreatedAt(Instant.now());
        fav.setUpdatedAt(Instant.now());
        fav.setUserId(dto.getUserId());
        fav.setFoodId(dto.getFoodId());
        fav.setName(dto.getName());
        fav.setOverrideProt(dto.getOverrideProt());
        fav.setOverrideCarb(dto.getOverrideCarb());
        fav.setOverrideFat(dto.getOverrideFat());
        fav.setOverrideCalorage(dto.getOverrideCalorage());
        return repository.save(fav);
    }

    @Override
    public Optional<FavoriteFoodDO> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FavoriteFoodDO> findAll() {
        var list = new ArrayList<FavoriteFoodDO>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public FavoriteFoodDO update(Long id, FavoriteFoodDTO dto) {
        FavoriteFoodDO fav = repository.findById(id).orElseThrow();
        fav.setUpdatedAt(Instant.now());
        fav.setUserId(dto.getUserId());
        fav.setFoodId(dto.getFoodId());
        fav.setName(dto.getName());
        fav.setOverrideProt(dto.getOverrideProt());
        fav.setOverrideCarb(dto.getOverrideCarb());
        fav.setOverrideFat(dto.getOverrideFat());
        fav.setOverrideCalorage(dto.getOverrideCalorage());
        return repository.save(fav);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
