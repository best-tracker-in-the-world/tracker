package ru.rogzy.tracker_backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rogzy.tracker_backend.repository.models.UserDO;

import java.util.Optional;

@Repository
public interface UserAuthorizationRepository extends CrudRepository<UserDO, Long> {
    Optional<UserDO> findByEmail(String email);

    @Transactional
    <S extends UserDO> S save(S entity);

}
