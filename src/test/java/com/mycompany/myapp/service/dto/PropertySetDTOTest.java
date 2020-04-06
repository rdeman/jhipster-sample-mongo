package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PropertySetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertySetDTO.class);
        PropertySetDTO propertySetDTO1 = new PropertySetDTO();
        propertySetDTO1.setId("id1");
        PropertySetDTO propertySetDTO2 = new PropertySetDTO();
        assertThat(propertySetDTO1).isNotEqualTo(propertySetDTO2);
        propertySetDTO2.setId(propertySetDTO1.getId());
        assertThat(propertySetDTO1).isEqualTo(propertySetDTO2);
        propertySetDTO2.setId("id2");
        assertThat(propertySetDTO1).isNotEqualTo(propertySetDTO2);
        propertySetDTO1.setId(null);
        assertThat(propertySetDTO1).isNotEqualTo(propertySetDTO2);
    }
}
