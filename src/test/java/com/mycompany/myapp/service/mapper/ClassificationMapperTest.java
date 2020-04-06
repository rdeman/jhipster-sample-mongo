package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassificationMapperTest {

    private ClassificationMapper classificationMapper;

    @BeforeEach
    public void setUp() {
        classificationMapper = new ClassificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(classificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classificationMapper.fromId(null)).isNull();
    }
}
