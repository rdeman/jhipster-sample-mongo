package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Property;
import com.mycompany.myapp.repository.PropertyRepository;
import com.mycompany.myapp.service.dto.PropertyDTO;
import com.mycompany.myapp.service.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Property}.
 */
@Service
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save.
     * @return the persisted entity.
     */
    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    /**
     * Get all the properties.
     *
     * @return the list of entities.
     */
    public List<PropertyDTO> findAll() {
        log.debug("Request to get all Properties");
        return propertyRepository.findAll().stream()
            .map(propertyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one property by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PropertyDTO> findOne(String id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findById(id)
            .map(propertyMapper::toDto);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }
}
