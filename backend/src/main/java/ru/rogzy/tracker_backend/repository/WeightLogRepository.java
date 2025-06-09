package ru.rogzy.tracker_backend.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.rogzy.tracker_backend.repository.models.WeightLogDO;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface WeightLogRepository extends CrudRepository<WeightLogDO, Long> {
    Optional<WeightLogDO> findByUserId(Long userId);
    @Query("SELECT * FROM weight_log WHERE created_at BETWEEN :from and :to and user_id = :userId")
    List<WeightLogDO> findAllByPeriod(Long userId, Instant from, Instant to);
    @Query("SELECT * FROM weight_log WHERE user_id = :userId AND created_at < :before ORDER BY created_at DESC LIMIT 1")
    List<WeightLogDO> findLatestBefore(Long userId, Instant before);
    @Modifying
    @Query("DELETE FROM weight_log WHERE user_id = :userId and id = :id")
    void deleteByUserIdAndId(Long userId, Long id);
    @Query("DELETE FROM weight_log WHERE user_id in :userIds")
    void deleteAllByUserIds(List<Long> userIds);
}
