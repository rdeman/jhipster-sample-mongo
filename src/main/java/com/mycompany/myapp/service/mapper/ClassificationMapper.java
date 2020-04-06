package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ClassificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classification} and its DTO {@link ClassificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassificationMapper extends EntityMapper<ClassificationDTO, Classification> {



    default Classification fromId(String id) {
        if (id == null) {
            return null;
        }
        Classification classification = new Classification();
        classification.setId(id);
        return classification;
    }
}
