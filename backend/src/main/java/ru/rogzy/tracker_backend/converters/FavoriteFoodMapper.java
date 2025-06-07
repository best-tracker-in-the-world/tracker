package ru.rogzy.tracker_backend.converters;

import org.mapstruct.Mapper;
import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.repository.models.FavoriteFoodDO;

@Mapper(componentModel = "spring")
public interface FavoriteFoodMapper {
    FavoriteFoodDTO doTODto(FavoriteFoodDO fl);
    FavoriteFoodDO dtoTODo(FavoriteFoodDTO fl);
}
