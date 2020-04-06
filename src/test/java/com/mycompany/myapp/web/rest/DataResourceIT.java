package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleMongoApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Data;
import com.mycompany.myapp.repository.DataRepository;
import com.mycompany.myapp.service.DataService;
import com.mycompany.myapp.service.dto.DataDTO;
import com.mycompany.myapp.service.mapper.DataMapper;

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

/**
 * Integration tests for the {@link DataResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterSampleMongoApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class DataResourceIT {

    private static final String DEFAULT_REVISION = "AAAAAAAAAA";
    private static final String UPDATED_REVISION = "BBBBBBBBBB";

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private DataService dataService;

    @Autowired
    private MockMvc restDataMockMvc;

    private Data data;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createEntity() {
        Data data = new Data()
            .revision(DEFAULT_REVISION);
        return data;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createUpdatedEntity() {
        Data data = new Data()
            .revision(UPDATED_REVISION);
        return data;
    }

    @BeforeEach
    public void initTest() {
        dataRepository.deleteAll();
        data = createEntity();
    }

    @Test
    public void createData() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);
        restDataMockMvc.perform(post("/api/data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isCreated());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate + 1);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getRevision()).isEqualTo(DEFAULT_REVISION);
    }

    @Test
    public void createDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // Create the Data with an existing ID
        data.setId("existing_id");
        DataDTO dataDTO = dataMapper.toDto(data);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataMockMvc.perform(post("/api/data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllData() throws Exception {
        // Initialize the database
        dataRepository.save(data);

        // Get all the dataList
        restDataMockMvc.perform(get("/api/data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(data.getId())))
            .andExpect(jsonPath("$.[*].revision").value(hasItem(DEFAULT_REVISION)));
    }
    
    @Test
    public void getData() throws Exception {
        // Initialize the database
        dataRepository.save(data);

        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(data.getId()))
            .andExpect(jsonPath("$.revision").value(DEFAULT_REVISION));
    }

    @Test
    public void getNonExistingData() throws Exception {
        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateData() throws Exception {
        // Initialize the database
        dataRepository.save(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data
        Data updatedData = dataRepository.findById(data.getId()).get();
        updatedData
            .revision(UPDATED_REVISION);
        DataDTO dataDTO = dataMapper.toDto(updatedData);

        restDataMockMvc.perform(put("/api/data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getRevision()).isEqualTo(UPDATED_REVISION);
    }

    @Test
    public void updateNonExistingData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMockMvc.perform(put("/api/data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteData() throws Exception {
        // Initialize the database
        dataRepository.save(data);

        int databaseSizeBeforeDelete = dataRepository.findAll().size();

        // Delete the data
        restDataMockMvc.perform(delete("/api/data/{id}", data.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
