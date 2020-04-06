package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.DataType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DataMaster} entity.
 */
public class DataMasterDTO implements Serializable {
    
    private String id;

    private String name;

    private DataType type;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataMasterDTO dataMasterDTO = (DataMasterDTO) o;
        if (dataMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
