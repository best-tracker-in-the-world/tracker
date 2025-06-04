package ru.rogzy.tracker_backend.converters;

import org.mapstruct.Mapper;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

@Mapper(componentModel = "spring")
public interface FoodLogMapper {
    FoodLogDTO doTODto(FoodLogDO fl);
    FoodLogDO dtoTODo(FoodLogDTO fl);
}
