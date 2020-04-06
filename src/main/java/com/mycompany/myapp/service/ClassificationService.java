package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Classification;
import com.mycompany.myapp.repository.ClassificationRepository;
import com.mycompany.myapp.service.dto.ClassificationDTO;
import com.mycompany.myapp.service.mapper.ClassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Classification}.
 */
@Service
public class ClassificationService {

    private final Logger log = LoggerFactory.getLogger(ClassificationService.class);

    private final ClassificationRepository classificationRepository;

    private final ClassificationMapper classificationMapper;

    public ClassificationService(ClassificationRepository classificationRepository, ClassificationMapper classificationMapper) {
        this.classificationRepository = classificationRepository;
        this.classificationMapper = classificationMapper;
    }

    /**
     * Save a classification.
     *
     * @param classificationDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassificationDTO save(ClassificationDTO classificationDTO) {
        log.debug("Request to save Classification : {}", classificationDTO);
        Classification classification = classificationMapper.toEntity(classificationDTO);
        classification = classificationRepository.save(classification);
        return classificationMapper.toDto(classification);
    }

    /**
     * Get all the classifications.
     *
     * @return the list of entities.
     */
    public List<ClassificationDTO> findAll() {
        log.debug("Request to get all Classifications");
        return classificationRepository.findAll().stream()
            .map(classificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one classification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ClassificationDTO> findOne(String id) {
        log.debug("Request to get Classification : {}", id);
        return classificationRepository.findById(id)
            .map(classificationMapper::toDto);
    }

    /**
     * Delete the classification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Classification : {}", id);
        classificationRepository.deleteById(id);
    }
}
