package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PropertyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyDTO.class);
        PropertyDTO propertyDTO1 = new PropertyDTO();
        propertyDTO1.setId("id1");
        PropertyDTO propertyDTO2 = new PropertyDTO();
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO2.setId(propertyDTO1.getId());
        assertThat(propertyDTO1).isEqualTo(propertyDTO2);
        propertyDTO2.setId("id2");
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO1.setId(null);
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
    }
}
