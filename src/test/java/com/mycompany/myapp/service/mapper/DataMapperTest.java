package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DataMapperTest {

    private DataMapper dataMapper;

    @BeforeEach
    public void setUp() {
        dataMapper = new DataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(dataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dataMapper.fromId(null)).isNull();
    }
}
