package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PropertySetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertySet.class);
        PropertySet propertySet1 = new PropertySet();
        propertySet1.setId("id1");
        PropertySet propertySet2 = new PropertySet();
        propertySet2.setId(propertySet1.getId());
        assertThat(propertySet1).isEqualTo(propertySet2);
        propertySet2.setId("id2");
        assertThat(propertySet1).isNotEqualTo(propertySet2);
        propertySet1.setId(null);
        assertThat(propertySet1).isNotEqualTo(propertySet2);
    }
}
