package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Data} entity.
 */
public class DataDTO implements Serializable {
    
    private String id;

    private String revision;


    private String classificationId;

    private String masterId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String dataMasterId) {
        this.masterId = dataMasterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataDTO dataDTO = (DataDTO) o;
        if (dataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataDTO{" +
            "id=" + getId() +
            ", revision='" + getRevision() + "'" +
            ", classificationId=" + getClassificationId() +
            ", masterId=" + getMasterId() +
            "}";
    }
}
