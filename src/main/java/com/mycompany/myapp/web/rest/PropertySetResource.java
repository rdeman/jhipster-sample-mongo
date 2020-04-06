package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PropertySetService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PropertySetDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.PropertySet}.
 */
@RestController
@RequestMapping("/api")
public class PropertySetResource {

    private final Logger log = LoggerFactory.getLogger(PropertySetResource.class);

    private static final String ENTITY_NAME = "propertySet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropertySetService propertySetService;

    public PropertySetResource(PropertySetService propertySetService) {
        this.propertySetService = propertySetService;
    }

    /**
     * {@code POST  /property-sets} : Create a new propertySet.
     *
     * @param propertySetDTO the propertySetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propertySetDTO, or with status {@code 400 (Bad Request)} if the propertySet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/property-sets")
    public ResponseEntity<PropertySetDTO> createPropertySet(@RequestBody PropertySetDTO propertySetDTO) throws URISyntaxException {
        log.debug("REST request to save PropertySet : {}", propertySetDTO);
        if (propertySetDTO.getId() != null) {
            throw new BadRequestAlertException("A new propertySet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertySetDTO result = propertySetService.save(propertySetDTO);
        return ResponseEntity.created(new URI("/api/property-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /property-sets} : Updates an existing propertySet.
     *
     * @param propertySetDTO the propertySetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propertySetDTO,
     * or with status {@code 400 (Bad Request)} if the propertySetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propertySetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/property-sets")
    public ResponseEntity<PropertySetDTO> updatePropertySet(@RequestBody PropertySetDTO propertySetDTO) throws URISyntaxException {
        log.debug("REST request to update PropertySet : {}", propertySetDTO);
        if (propertySetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertySetDTO result = propertySetService.save(propertySetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propertySetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /property-sets} : get all the propertySets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propertySets in body.
     */
    @GetMapping("/property-sets")
    public List<PropertySetDTO> getAllPropertySets() {
        log.debug("REST request to get all PropertySets");
        return propertySetService.findAll();
    }

    /**
     * {@code GET  /property-sets/:id} : get the "id" propertySet.
     *
     * @param id the id of the propertySetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertySetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/property-sets/{id}")
    public ResponseEntity<PropertySetDTO> getPropertySet(@PathVariable String id) {
        log.debug("REST request to get PropertySet : {}", id);
        Optional<PropertySetDTO> propertySetDTO = propertySetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertySetDTO);
    }

    /**
     * {@code DELETE  /property-sets/:id} : delete the "id" propertySet.
     *
     * @param id the id of the propertySetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/property-sets/{id}")
    public ResponseEntity<Void> deletePropertySet(@PathVariable String id) {
        log.debug("REST request to delete PropertySet : {}", id);
        propertySetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
