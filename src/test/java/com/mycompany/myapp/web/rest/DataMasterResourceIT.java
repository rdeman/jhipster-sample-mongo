package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleMongoApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.DataMaster;
import com.mycompany.myapp.repository.DataMasterRepository;
import com.mycompany.myapp.service.DataMasterService;
import com.mycompany.myapp.service.dto.DataMasterDTO;
import com.mycompany.myapp.service.mapper.DataMasterMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.DataType;
/**
 * Integration tests for the {@link DataMasterResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterSampleMongoApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class DataMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_TYPE = DataType.MATERIAL;
    private static final DataType UPDATED_TYPE = DataType.TEST;

    @Autowired
    private DataMasterRepository dataMasterRepository;

    @Autowired
    private DataMasterMapper dataMasterMapper;

    @Autowired
    private DataMasterService dataMasterService;

    @Autowired
    private MockMvc restDataMasterMockMvc;

    private DataMaster dataMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataMaster createEntity() {
        DataMaster dataMaster = new DataMaster()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return dataMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataMaster createUpdatedEntity() {
        DataMaster dataMaster = new DataMaster()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        return dataMaster;
    }

    @BeforeEach
    public void initTest() {
        dataMasterRepository.deleteAll();
        dataMaster = createEntity();
    }

    @Test
    public void createDataMaster() throws Exception {
        int databaseSizeBeforeCreate = dataMasterRepository.findAll().size();

        // Create the DataMaster
        DataMasterDTO dataMasterDTO = dataMasterMapper.toDto(dataMaster);
        restDataMasterMockMvc.perform(post("/api/data-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the DataMaster in the database
        List<DataMaster> dataMasterList = dataMasterRepository.findAll();
        assertThat(dataMasterList).hasSize(databaseSizeBeforeCreate + 1);
        DataMaster testDataMaster = dataMasterList.get(dataMasterList.size() - 1);
        assertThat(testDataMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataMaster.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createDataMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataMasterRepository.findAll().size();

        // Create the DataMaster with an existing ID
        dataMaster.setId("existing_id");
        DataMasterDTO dataMasterDTO = dataMasterMapper.toDto(dataMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataMasterMockMvc.perform(post("/api/data-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataMaster in the database
        List<DataMaster> dataMasterList = dataMasterRepository.findAll();
        assertThat(dataMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDataMasters() throws Exception {
        // Initialize the database
        dataMasterRepository.save(dataMaster);

        // Get all the dataMasterList
        restDataMasterMockMvc.perform(get("/api/data-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataMaster.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getDataMaster() throws Exception {
        // Initialize the database
        dataMasterRepository.save(dataMaster);

        // Get the dataMaster
        restDataMasterMockMvc.perform(get("/api/data-masters/{id}", dataMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataMaster.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingDataMaster() throws Exception {
        // Get the dataMaster
        restDataMasterMockMvc.perform(get("/api/data-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDataMaster() throws Exception {
        // Initialize the database
        dataMasterRepository.save(dataMaster);

        int databaseSizeBeforeUpdate = dataMasterRepository.findAll().size();

        // Update the dataMaster
        DataMaster updatedDataMaster = dataMasterRepository.findById(dataMaster.getId()).get();
        updatedDataMaster
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        DataMasterDTO dataMasterDTO = dataMasterMapper.toDto(updatedDataMaster);

        restDataMasterMockMvc.perform(put("/api/data-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataMasterDTO)))
            .andExpect(status().isOk());

        // Validate the DataMaster in the database
        List<DataMaster> dataMasterList = dataMasterRepository.findAll();
        assertThat(dataMasterList).hasSize(databaseSizeBeforeUpdate);
        DataMaster testDataMaster = dataMasterList.get(dataMasterList.size() - 1);
        assertThat(testDataMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataMaster.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingDataMaster() throws Exception {
        int databaseSizeBeforeUpdate = dataMasterRepository.findAll().size();

        // Create the DataMaster
        DataMasterDTO dataMasterDTO = dataMasterMapper.toDto(dataMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMasterMockMvc.perform(put("/api/data-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataMaster in the database
        List<DataMaster> dataMasterList = dataMasterRepository.findAll();
        assertThat(dataMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDataMaster() throws Exception {
        // Initialize the database
        dataMasterRepository.save(dataMaster);

        int databaseSizeBeforeDelete = dataMasterRepository.findAll().size();

        // Delete the dataMaster
        restDataMasterMockMvc.perform(delete("/api/data-masters/{id}", dataMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataMaster> dataMasterList = dataMasterRepository.findAll();
        assertThat(dataMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
