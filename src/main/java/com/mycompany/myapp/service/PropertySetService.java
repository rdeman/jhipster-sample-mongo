package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PropertySet;
import com.mycompany.myapp.repository.PropertySetRepository;
import com.mycompany.myapp.service.dto.PropertySetDTO;
import com.mycompany.myapp.service.mapper.PropertySetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PropertySet}.
 */
@Service
public class PropertySetService {

    private final Logger log = LoggerFactory.getLogger(PropertySetService.class);

    private final PropertySetRepository propertySetRepository;

    private final PropertySetMapper propertySetMapper;

    public PropertySetService(PropertySetRepository propertySetRepository, PropertySetMapper propertySetMapper) {
        this.propertySetRepository = propertySetRepository;
        this.propertySetMapper = propertySetMapper;
    }

    /**
     * Save a propertySet.
     *
     * @param propertySetDTO the entity to save.
     * @return the persisted entity.
     */
    public PropertySetDTO save(PropertySetDTO propertySetDTO) {
        log.debug("Request to save PropertySet : {}", propertySetDTO);
        PropertySet propertySet = propertySetMapper.toEntity(propertySetDTO);
        propertySet = propertySetRepository.save(propertySet);
        return propertySetMapper.toDto(propertySet);
    }

    /**
     * Get all the propertySets.
     *
     * @return the list of entities.
     */
    public List<PropertySetDTO> findAll() {
        log.debug("Request to get all PropertySets");
        return propertySetRepository.findAll().stream()
            .map(propertySetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one propertySet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PropertySetDTO> findOne(String id) {
        log.debug("Request to get PropertySet : {}", id);
        return propertySetRepository.findById(id)
            .map(propertySetMapper::toDto);
    }

    /**
     * Delete the propertySet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PropertySet : {}", id);
        propertySetRepository.deleteById(id);
    }
}
