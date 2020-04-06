package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertySetMapperTest {

    private PropertySetMapper propertySetMapper;

    @BeforeEach
    public void setUp() {
        propertySetMapper = new PropertySetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(propertySetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(propertySetMapper.fromId(null)).isNull();
    }
}
