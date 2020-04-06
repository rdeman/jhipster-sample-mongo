package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Classification.
 */
@Document(collection = "classification")
public class Classification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("class_att_1")
    private String classAtt1;

    @Field("class_att_2")
    private String classAtt2;

    @Field("class_att_3")
    private String classAtt3;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassAtt1() {
        return classAtt1;
    }

    public Classification classAtt1(String classAtt1) {
        this.classAtt1 = classAtt1;
        return this;
    }

    public void setClassAtt1(String classAtt1) {
        this.classAtt1 = classAtt1;
    }

    public String getClassAtt2() {
        return classAtt2;
    }

    public Classification classAtt2(String classAtt2) {
        this.classAtt2 = classAtt2;
        return this;
    }

    public void setClassAtt2(String classAtt2) {
        this.classAtt2 = classAtt2;
    }

    public String getClassAtt3() {
        return classAtt3;
    }

    public Classification classAtt3(String classAtt3) {
        this.classAtt3 = classAtt3;
        return this;
    }

    public void setClassAtt3(String classAtt3) {
        this.classAtt3 = classAtt3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classification)) {
            return false;
        }
        return id != null && id.equals(((Classification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Classification{" +
            "id=" + getId() +
            ", classAtt1='" + getClassAtt1() + "'" +
            ", classAtt2='" + getClassAtt2() + "'" +
            ", classAtt3='" + getClassAtt3() + "'" +
            "}";
    }
}
