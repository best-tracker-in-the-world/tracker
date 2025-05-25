package ru.rogzy.tracker_backend.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

public interface FavoriteFoodRepository extends CrudRepository<FavoriteFoodDO, Long> {
}
