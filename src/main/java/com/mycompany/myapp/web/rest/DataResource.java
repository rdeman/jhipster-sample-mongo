package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DataService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DataDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Data}.
 */
@RestController
@RequestMapping("/api")
public class DataResource {

    private final Logger log = LoggerFactory.getLogger(DataResource.class);

    private static final String ENTITY_NAME = "data";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataService dataService;

    public DataResource(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * {@code POST  /data} : Create a new data.
     *
     * @param dataDTO the dataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataDTO, or with status {@code 400 (Bad Request)} if the data has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data")
    public ResponseEntity<DataDTO> createData(@RequestBody DataDTO dataDTO) throws URISyntaxException {
        log.debug("REST request to save Data : {}", dataDTO);
        if (dataDTO.getId() != null) {
            throw new BadRequestAlertException("A new data cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataDTO result = dataService.save(dataDTO);
        return ResponseEntity.created(new URI("/api/data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data} : Updates an existing data.
     *
     * @param dataDTO the dataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataDTO,
     * or with status {@code 400 (Bad Request)} if the dataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data")
    public ResponseEntity<DataDTO> updateData(@RequestBody DataDTO dataDTO) throws URISyntaxException {
        log.debug("REST request to update Data : {}", dataDTO);
        if (dataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataDTO result = dataService.save(dataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data} : get all the data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of data in body.
     */
    @GetMapping("/data")
    public List<DataDTO> getAllData() {
        log.debug("REST request to get all Data");
        return dataService.findAll();
    }

    /**
     * {@code GET  /data/:id} : get the "id" data.
     *
     * @param id the id of the dataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data/{id}")
    public ResponseEntity<DataDTO> getData(@PathVariable String id) {
        log.debug("REST request to get Data : {}", id);
        Optional<DataDTO> dataDTO = dataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataDTO);
    }

    /**
     * {@code DELETE  /data/:id} : delete the "id" data.
     *
     * @param id the id of the dataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable String id) {
        log.debug("REST request to delete Data : {}", id);
        dataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
