package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Classification} entity.
 */
public class ClassificationDTO implements Serializable {
    
    private String id;

    private String classAtt1;

    private String classAtt2;

    private String classAtt3;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassAtt1() {
        return classAtt1;
    }

    public void setClassAtt1(String classAtt1) {
        this.classAtt1 = classAtt1;
    }

    public String getClassAtt2() {
        return classAtt2;
    }

    public void setClassAtt2(String classAtt2) {
        this.classAtt2 = classAtt2;
    }

    public String getClassAtt3() {
        return classAtt3;
    }

    public void setClassAtt3(String classAtt3) {
        this.classAtt3 = classAtt3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassificationDTO classificationDTO = (ClassificationDTO) o;
        if (classificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassificationDTO{" +
            "id=" + getId() +
            ", classAtt1='" + getClassAtt1() + "'" +
            ", classAtt2='" + getClassAtt2() + "'" +
            ", classAtt3='" + getClassAtt3() + "'" +
            "}";
    }
}
