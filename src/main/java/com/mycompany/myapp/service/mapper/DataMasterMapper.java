package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DataMasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataMaster} and its DTO {@link DataMasterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataMasterMapper extends EntityMapper<DataMasterDTO, DataMaster> {



    default DataMaster fromId(String id) {
        if (id == null) {
            return null;
        }
        DataMaster dataMaster = new DataMaster();
        dataMaster.setId(id);
        return dataMaster;
    }
}
