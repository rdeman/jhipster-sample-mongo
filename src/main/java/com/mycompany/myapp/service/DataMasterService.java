package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DataMaster;
import com.mycompany.myapp.repository.DataMasterRepository;
import com.mycompany.myapp.service.dto.DataMasterDTO;
import com.mycompany.myapp.service.mapper.DataMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DataMaster}.
 */
@Service
public class DataMasterService {

    private final Logger log = LoggerFactory.getLogger(DataMasterService.class);

    private final DataMasterRepository dataMasterRepository;

    private final DataMasterMapper dataMasterMapper;

    public DataMasterService(DataMasterRepository dataMasterRepository, DataMasterMapper dataMasterMapper) {
        this.dataMasterRepository = dataMasterRepository;
        this.dataMasterMapper = dataMasterMapper;
    }

    /**
     * Save a dataMaster.
     *
     * @param dataMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public DataMasterDTO save(DataMasterDTO dataMasterDTO) {
        log.debug("Request to save DataMaster : {}", dataMasterDTO);
        DataMaster dataMaster = dataMasterMapper.toEntity(dataMasterDTO);
        dataMaster = dataMasterRepository.save(dataMaster);
        return dataMasterMapper.toDto(dataMaster);
    }

    /**
     * Get all the dataMasters.
     *
     * @return the list of entities.
     */
    public List<DataMasterDTO> findAll() {
        log.debug("Request to get all DataMasters");
        return dataMasterRepository.findAll().stream()
            .map(dataMasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dataMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DataMasterDTO> findOne(String id) {
        log.debug("Request to get DataMaster : {}", id);
        return dataMasterRepository.findById(id)
            .map(dataMasterMapper::toDto);
    }

    /**
     * Delete the dataMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete DataMaster : {}", id);
        dataMasterRepository.deleteById(id);
    }
}
