package ru.rogzy.tracker_backend.converters;

import org.mapstruct.Mapper;
import ru.rogzy.tracker_backend.controller.models.WeightLogDTO;
import ru.rogzy.tracker_backend.repository.models.WeightLogDO;

@Mapper(componentModel = "spring")
public interface WeightLogMapper {
    WeightLogDTO doTODto(WeightLogDO fl);
    WeightLogDO dtoTODo(WeightLogDTO fl);
}
