package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ClassificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classification.class);
        Classification classification1 = new Classification();
        classification1.setId("id1");
        Classification classification2 = new Classification();
        classification2.setId(classification1.getId());
        assertThat(classification1).isEqualTo(classification2);
        classification2.setId("id2");
        assertThat(classification1).isNotEqualTo(classification2);
        classification1.setId(null);
        assertThat(classification1).isNotEqualTo(classification2);
    }
}
