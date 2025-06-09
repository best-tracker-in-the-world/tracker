package ru.rogzy.tracker_backend.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface FoodLogRepository extends CrudRepository<FoodLogDO, Long>, PagingAndSortingRepository<FoodLogDO, Long> {
    Optional<FoodLogDO> findByUserIdAndId(Long userId, Long id);
    @Modifying
    @Query("DELETE FROM food_log WHERE user_id = :userId and id = :id")
    void deleteByUserIdAndId(Long userId, Long id);
    @Query("SELECT * FROM food_log WHERE created_at BETWEEN :from and :to and user_id = :userId")
    List<FoodLogDO> findAllByPeriod(Long userId, Instant from, Instant to);
    @Query("DELETE FROM food_log WHERE user_id in :userIds")
    void deleteAllByUserIds(List<Long> userIds);
}
