package ru.rogzy.tracker_backend.converters;

import org.mapstruct.Mapper;
import ru.rogzy.tracker_backend.controller.models.GoalLogDTO;
import ru.rogzy.tracker_backend.repository.models.GoalLogDO;

@Mapper(componentModel = "spring")
public interface GoalLogMapper {
    GoalLogDTO doTODto(GoalLogDO fl);
    GoalLogDO dtoTODo(GoalLogDTO fl);
}
