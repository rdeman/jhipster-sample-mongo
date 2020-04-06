package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Data} and its DTO {@link DataDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassificationMapper.class, DataMasterMapper.class})
public interface DataMapper extends EntityMapper<DataDTO, Data> {

    @Mapping(source = "classification.id", target = "classificationId")
    @Mapping(source = "master.id", target = "masterId")
    DataDTO toDto(Data data);

    @Mapping(source = "classificationId", target = "classification")
    @Mapping(target = "propertySets", ignore = true)
    @Mapping(target = "removePropertySet", ignore = true)
    @Mapping(source = "masterId", target = "master")
    Data toEntity(DataDTO dataDTO);

    default Data fromId(String id) {
        if (id == null) {
            return null;
        }
        Data data = new Data();
        data.setId(id);
        return data;
    }
}
