package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PropertyService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PropertyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Property}.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private static final String ENTITY_NAME = "property";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropertyService propertyService;

    public PropertyResource(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * {@code POST  /properties} : Create a new property.
     *
     * @param propertyDTO the propertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propertyDTO, or with status {@code 400 (Bad Request)} if the property has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);
        if (propertyDTO.getId() != null) {
            throw new BadRequestAlertException("A new property cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyDTO result = propertyService.save(propertyDTO);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /properties} : Updates an existing property.
     *
     * @param propertyDTO the propertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propertyDTO,
     * or with status {@code 400 (Bad Request)} if the propertyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/properties")
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyDTO result = propertyService.save(propertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /properties} : get all the properties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of properties in body.
     */
    @GetMapping("/properties")
    public List<PropertyDTO> getAllProperties() {
        log.debug("REST request to get all Properties");
        return propertyService.findAll();
    }

    /**
     * {@code GET  /properties/:id} : get the "id" property.
     *
     * @param id the id of the propertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/properties/{id}")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable String id) {
        log.debug("REST request to get Property : {}", id);
        Optional<PropertyDTO> propertyDTO = propertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }

    /**
     * {@code DELETE  /properties/:id} : delete the "id" property.
     *
     * @param id the id of the propertyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/properties/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        log.debug("REST request to delete Property : {}", id);
        propertyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
