package ru.rogzy.tracker_backend.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

import java.util.List;
import java.util.Optional;

public interface FavoriteFoodRepository extends CrudRepository<FavoriteFoodDO, Long>, PagingAndSortingRepository<FavoriteFoodDO, Long> {
    Optional<FavoriteFoodDO> findByUserIdAndId(Long userId, Long id);
    @Modifying
    @Query("DELETE FROM favorite_foods WHERE user_id = :userId and id = :id")
    void deleteByUserIdAndId(Long userId, Long id);
    @Override
    List<FavoriteFoodDO> findAll();
}
