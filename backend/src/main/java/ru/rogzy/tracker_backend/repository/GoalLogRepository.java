package ru.rogzy.tracker_backend.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.rogzy.tracker_backend.repository.models.GoalLogDO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface GoalLogRepository extends CrudRepository<GoalLogDO, Long> {
    Optional<GoalLogDO> findByUserId(Long userId);
    @Query("SELECT * FROM goal_log WHERE created_at BETWEEN :from and :to and user_id = :userId")
    List<GoalLogDO> findAllByPeriod(Long userId, Instant from, Instant to);
    @Query("SELECT * FROM goal_log WHERE user_id = :userId AND created_at < :before ORDER BY created_at DESC LIMIT 1")
    List<GoalLogDO> findLatestBefore(Long userId, Instant before);
    @Query("DELETE FROM goal_log WHERE user_id in :userIds")
    void deleteAllByUserIds(List<Long> userIds);
}
