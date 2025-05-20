package ru.rogzy.tracker_backend.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rogzy.tracker_backend.repository.models.FoodDO;

public interface FoodRepository extends CrudRepository<FoodDO, Long> {}

