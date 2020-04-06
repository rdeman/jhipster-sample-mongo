package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DataMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataMasterDTO.class);
        DataMasterDTO dataMasterDTO1 = new DataMasterDTO();
        dataMasterDTO1.setId("id1");
        DataMasterDTO dataMasterDTO2 = new DataMasterDTO();
        assertThat(dataMasterDTO1).isNotEqualTo(dataMasterDTO2);
        dataMasterDTO2.setId(dataMasterDTO1.getId());
        assertThat(dataMasterDTO1).isEqualTo(dataMasterDTO2);
        dataMasterDTO2.setId("id2");
        assertThat(dataMasterDTO1).isNotEqualTo(dataMasterDTO2);
        dataMasterDTO1.setId(null);
        assertThat(dataMasterDTO1).isNotEqualTo(dataMasterDTO2);
    }
}
