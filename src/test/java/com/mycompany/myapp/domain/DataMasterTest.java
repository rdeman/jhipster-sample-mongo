package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DataMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataMaster.class);
        DataMaster dataMaster1 = new DataMaster();
        dataMaster1.setId("id1");
        DataMaster dataMaster2 = new DataMaster();
        dataMaster2.setId(dataMaster1.getId());
        assertThat(dataMaster1).isEqualTo(dataMaster2);
        dataMaster2.setId("id2");
        assertThat(dataMaster1).isNotEqualTo(dataMaster2);
        dataMaster1.setId(null);
        assertThat(dataMaster1).isNotEqualTo(dataMaster2);
    }
}
