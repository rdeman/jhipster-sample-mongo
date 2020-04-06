package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A PropertySet.
 */
@Document(collection = "property_set")
public class PropertySet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @DBRef
    @Field("property")
    private Set<Property> properties = new HashSet<>();

    @DBRef
    @Field("data")
    @JsonIgnoreProperties("propertySets")
    private Data data;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PropertySet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public PropertySet properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public PropertySet addProperty(Property property) {
        this.properties.add(property);
        property.setPropertySet(this);
        return this;
    }

    public PropertySet removeProperty(Property property) {
        this.properties.remove(property);
        property.setPropertySet(null);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Data getData() {
        return data;
    }

    public PropertySet data(Data data) {
        this.data = data;
        return this;
    }

    public void setData(Data data) {
        this.data = data;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertySet)) {
            return false;
        }
        return id != null && id.equals(((PropertySet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PropertySet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
