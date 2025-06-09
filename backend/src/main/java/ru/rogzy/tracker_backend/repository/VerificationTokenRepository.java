package ru.rogzy.tracker_backend.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rogzy.tracker_backend.repository.models.VerificationTokenDO;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationTokenDO, Long> {
    Optional<VerificationTokenDO> findByToken(String token);
    void deleteByToken(String token);
    @Override
    @Transactional
    <S extends VerificationTokenDO> S save(S entity);
    @Query("DELETE FROM verification_tokens WHERE user_id in :userIds")
    void deleteAllByUserIds(List<Long> userIds);
}