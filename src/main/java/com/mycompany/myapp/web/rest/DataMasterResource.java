package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DataMasterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DataMasterDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.DataMaster}.
 */
@RestController
@RequestMapping("/api")
public class DataMasterResource {

    private final Logger log = LoggerFactory.getLogger(DataMasterResource.class);

    private static final String ENTITY_NAME = "dataMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataMasterService dataMasterService;

    public DataMasterResource(DataMasterService dataMasterService) {
        this.dataMasterService = dataMasterService;
    }

    /**
     * {@code POST  /data-masters} : Create a new dataMaster.
     *
     * @param dataMasterDTO the dataMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataMasterDTO, or with status {@code 400 (Bad Request)} if the dataMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-masters")
    public ResponseEntity<DataMasterDTO> createDataMaster(@RequestBody DataMasterDTO dataMasterDTO) throws URISyntaxException {
        log.debug("REST request to save DataMaster : {}", dataMasterDTO);
        if (dataMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataMasterDTO result = dataMasterService.save(dataMasterDTO);
        return ResponseEntity.created(new URI("/api/data-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-masters} : Updates an existing dataMaster.
     *
     * @param dataMasterDTO the dataMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataMasterDTO,
     * or with status {@code 400 (Bad Request)} if the dataMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-masters")
    public ResponseEntity<DataMasterDTO> updateDataMaster(@RequestBody DataMasterDTO dataMasterDTO) throws URISyntaxException {
        log.debug("REST request to update DataMaster : {}", dataMasterDTO);
        if (dataMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataMasterDTO result = dataMasterService.save(dataMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-masters} : get all the dataMasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataMasters in body.
     */
    @GetMapping("/data-masters")
    public List<DataMasterDTO> getAllDataMasters() {
        log.debug("REST request to get all DataMasters");
        return dataMasterService.findAll();
    }

    /**
     * {@code GET  /data-masters/:id} : get the "id" dataMaster.
     *
     * @param id the id of the dataMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-masters/{id}")
    public ResponseEntity<DataMasterDTO> getDataMaster(@PathVariable String id) {
        log.debug("REST request to get DataMaster : {}", id);
        Optional<DataMasterDTO> dataMasterDTO = dataMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataMasterDTO);
    }

    /**
     * {@code DELETE  /data-masters/:id} : delete the "id" dataMaster.
     *
     * @param id the id of the dataMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-masters/{id}")
    public ResponseEntity<Void> deleteDataMaster(@PathVariable String id) {
        log.debug("REST request to delete DataMaster : {}", id);
        dataMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
