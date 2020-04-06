package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataDTO.class);
        DataDTO dataDTO1 = new DataDTO();
        dataDTO1.setId("id1");
        DataDTO dataDTO2 = new DataDTO();
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
        dataDTO2.setId(dataDTO1.getId());
        assertThat(dataDTO1).isEqualTo(dataDTO2);
        dataDTO2.setId("id2");
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
        dataDTO1.setId(null);
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
    }
}
