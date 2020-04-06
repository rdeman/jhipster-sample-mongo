package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PropertySetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PropertySet} and its DTO {@link PropertySetDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataMapper.class})
public interface PropertySetMapper extends EntityMapper<PropertySetDTO, PropertySet> {

    @Mapping(source = "data.id", target = "dataId")
    PropertySetDTO toDto(PropertySet propertySet);

    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "removeProperty", ignore = true)
    @Mapping(source = "dataId", target = "data")
    PropertySet toEntity(PropertySetDTO propertySetDTO);

    default PropertySet fromId(String id) {
        if (id == null) {
            return null;
        }
        PropertySet propertySet = new PropertySet();
        propertySet.setId(id);
        return propertySet;
    }
}
