package ru.rogzy.tracker_backend.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;

import java.time.Instant;

@SpringBootTest
public class FoodLogMapperTest {
    @Autowired
    private FoodLogMapper foodLogMapper;

    @Test
    void doTODtoTest() {
        var d = new FoodLogDO();
        d.setCreatedAt(Instant.now());
        d.setUpdatedAt(Instant.now());
        d.setId(1L);
        var exp = new FoodLogDTO();
        exp.setId(1L);
        //act
        var dto = foodLogMapper.doTODto(d);
        //assert
        Assertions.assertThat(dto).isEqualTo(exp);
    }
}
