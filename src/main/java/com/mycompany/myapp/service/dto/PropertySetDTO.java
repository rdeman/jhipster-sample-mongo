package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PropertySet} entity.
 */
public class PropertySetDTO implements Serializable {
    
    private String id;

    private String name;


    private String dataId;
    
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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertySetDTO propertySetDTO = (PropertySetDTO) o;
        if (propertySetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertySetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertySetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dataId=" + getDataId() +
            "}";
    }
}
