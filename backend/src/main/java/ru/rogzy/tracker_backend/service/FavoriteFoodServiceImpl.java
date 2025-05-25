package ru.rogzy.tracker_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.repository.FavoriteFoodRepository;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class FavoriteFoodServiceImpl implements FavoriteFoodService {
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
        return repository.findAll();
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
