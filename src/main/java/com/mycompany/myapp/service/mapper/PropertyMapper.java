package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Property} and its DTO {@link PropertyDTO}.
 */
@Mapper(componentModel = "spring", uses = {PropertySetMapper.class})
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {

    @Mapping(source = "propertySet.id", target = "propertySetId")
    PropertyDTO toDto(Property property);

    @Mapping(source = "propertySetId", target = "propertySet")
    Property toEntity(PropertyDTO propertyDTO);

    default Property fromId(String id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
