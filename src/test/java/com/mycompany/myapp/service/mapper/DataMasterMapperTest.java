package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DataMasterMapperTest {

    private DataMasterMapper dataMasterMapper;

    @BeforeEach
    public void setUp() {
        dataMasterMapper = new DataMasterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(dataMasterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dataMasterMapper.fromId(null)).isNull();
    }
}
