package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Data;
import com.mycompany.myapp.repository.DataRepository;
import com.mycompany.myapp.service.dto.DataDTO;
import com.mycompany.myapp.service.mapper.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Data}.
 */
@Service
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    private final DataRepository dataRepository;

    private final DataMapper dataMapper;

    public DataService(DataRepository dataRepository, DataMapper dataMapper) {
        this.dataRepository = dataRepository;
        this.dataMapper = dataMapper;
    }

    /**
     * Save a data.
     *
     * @param dataDTO the entity to save.
     * @return the persisted entity.
     */
    public DataDTO save(DataDTO dataDTO) {
        log.debug("Request to save Data : {}", dataDTO);
        Data data = dataMapper.toEntity(dataDTO);
        data = dataRepository.save(data);
        return dataMapper.toDto(data);
    }

    /**
     * Get all the data.
     *
     * @return the list of entities.
     */
    public List<DataDTO> findAll() {
        log.debug("Request to get all Data");
        return dataRepository.findAll().stream()
            .map(dataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one data by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DataDTO> findOne(String id) {
        log.debug("Request to get Data : {}", id);
        return dataRepository.findById(id)
            .map(dataMapper::toDto);
    }

    /**
     * Delete the data by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Data : {}", id);
        dataRepository.deleteById(id);
    }
}
