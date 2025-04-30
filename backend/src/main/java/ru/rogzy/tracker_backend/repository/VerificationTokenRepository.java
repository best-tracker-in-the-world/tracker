package ru.rogzy.tracker_backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rogzy.tracker_backend.repository.models.VerificationTokenDO;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationTokenDO, Long> {
    Optional<VerificationTokenDO> findByToken(String token);
    void deleteByToken(String token);
    @Override
    @Transactional
    <S extends VerificationTokenDO> S save(S entity);
}