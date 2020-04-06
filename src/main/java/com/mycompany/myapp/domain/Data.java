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
 * A Data.
 */
@Document(collection = "data")
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("revision")
    private String revision;

    @DBRef
    @Field("classification")
    private Classification classification;

    @DBRef
    @Field("propertySet")
    private Set<PropertySet> propertySets = new HashSet<>();

    @DBRef
    @Field("master")
    @JsonIgnoreProperties("data")
    private DataMaster master;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public Data revision(String revision) {
        this.revision = revision;
        return this;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Classification getClassification() {
        return classification;
    }

    public Data classification(Classification classification) {
        this.classification = classification;
        return this;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Set<PropertySet> getPropertySets() {
        return propertySets;
    }

    public Data propertySets(Set<PropertySet> propertySets) {
        this.propertySets = propertySets;
        return this;
    }

    public Data addPropertySet(PropertySet propertySet) {
        this.propertySets.add(propertySet);
        propertySet.setData(this);
        return this;
    }

    public Data removePropertySet(PropertySet propertySet) {
        this.propertySets.remove(propertySet);
        propertySet.setData(null);
        return this;
    }

    public void setPropertySets(Set<PropertySet> propertySets) {
        this.propertySets = propertySets;
    }

    public DataMaster getMaster() {
        return master;
    }

    public Data master(DataMaster dataMaster) {
        this.master = dataMaster;
        return this;
    }

    public void setMaster(DataMaster dataMaster) {
        this.master = dataMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Data)) {
            return false;
        }
        return id != null && id.equals(((Data) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Data{" +
            "id=" + getId() +
            ", revision='" + getRevision() + "'" +
            "}";
    }
}
