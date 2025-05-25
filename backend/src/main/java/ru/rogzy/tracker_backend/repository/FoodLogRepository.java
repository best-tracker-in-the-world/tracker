package ru.rogzy.tracker_backend.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

public interface FoodLogRepository extends CrudRepository<FoodLogDO, Long> {
}
