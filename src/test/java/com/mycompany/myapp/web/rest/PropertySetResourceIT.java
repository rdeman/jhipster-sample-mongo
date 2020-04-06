package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleMongoApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.PropertySet;
import com.mycompany.myapp.repository.PropertySetRepository;
import com.mycompany.myapp.service.PropertySetService;
import com.mycompany.myapp.service.dto.PropertySetDTO;
import com.mycompany.myapp.service.mapper.PropertySetMapper;

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
 * Integration tests for the {@link PropertySetResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterSampleMongoApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class PropertySetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PropertySetRepository propertySetRepository;

    @Autowired
    private PropertySetMapper propertySetMapper;

    @Autowired
    private PropertySetService propertySetService;

    @Autowired
    private MockMvc restPropertySetMockMvc;

    private PropertySet propertySet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropertySet createEntity() {
        PropertySet propertySet = new PropertySet()
            .name(DEFAULT_NAME);
        return propertySet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropertySet createUpdatedEntity() {
        PropertySet propertySet = new PropertySet()
            .name(UPDATED_NAME);
        return propertySet;
    }

    @BeforeEach
    public void initTest() {
        propertySetRepository.deleteAll();
        propertySet = createEntity();
    }

    @Test
    public void createPropertySet() throws Exception {
        int databaseSizeBeforeCreate = propertySetRepository.findAll().size();

        // Create the PropertySet
        PropertySetDTO propertySetDTO = propertySetMapper.toDto(propertySet);
        restPropertySetMockMvc.perform(post("/api/property-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertySetDTO)))
            .andExpect(status().isCreated());

        // Validate the PropertySet in the database
        List<PropertySet> propertySetList = propertySetRepository.findAll();
        assertThat(propertySetList).hasSize(databaseSizeBeforeCreate + 1);
        PropertySet testPropertySet = propertySetList.get(propertySetList.size() - 1);
        assertThat(testPropertySet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createPropertySetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertySetRepository.findAll().size();

        // Create the PropertySet with an existing ID
        propertySet.setId("existing_id");
        PropertySetDTO propertySetDTO = propertySetMapper.toDto(propertySet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertySetMockMvc.perform(post("/api/property-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertySetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertySet in the database
        List<PropertySet> propertySetList = propertySetRepository.findAll();
        assertThat(propertySetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPropertySets() throws Exception {
        // Initialize the database
        propertySetRepository.save(propertySet);

        // Get all the propertySetList
        restPropertySetMockMvc.perform(get("/api/property-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertySet.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getPropertySet() throws Exception {
        // Initialize the database
        propertySetRepository.save(propertySet);

        // Get the propertySet
        restPropertySetMockMvc.perform(get("/api/property-sets/{id}", propertySet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(propertySet.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    public void getNonExistingPropertySet() throws Exception {
        // Get the propertySet
        restPropertySetMockMvc.perform(get("/api/property-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePropertySet() throws Exception {
        // Initialize the database
        propertySetRepository.save(propertySet);

        int databaseSizeBeforeUpdate = propertySetRepository.findAll().size();

        // Update the propertySet
        PropertySet updatedPropertySet = propertySetRepository.findById(propertySet.getId()).get();
        updatedPropertySet
            .name(UPDATED_NAME);
        PropertySetDTO propertySetDTO = propertySetMapper.toDto(updatedPropertySet);

        restPropertySetMockMvc.perform(put("/api/property-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertySetDTO)))
            .andExpect(status().isOk());

        // Validate the PropertySet in the database
        List<PropertySet> propertySetList = propertySetRepository.findAll();
        assertThat(propertySetList).hasSize(databaseSizeBeforeUpdate);
        PropertySet testPropertySet = propertySetList.get(propertySetList.size() - 1);
        assertThat(testPropertySet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingPropertySet() throws Exception {
        int databaseSizeBeforeUpdate = propertySetRepository.findAll().size();

        // Create the PropertySet
        PropertySetDTO propertySetDTO = propertySetMapper.toDto(propertySet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertySetMockMvc.perform(put("/api/property-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertySetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertySet in the database
        List<PropertySet> propertySetList = propertySetRepository.findAll();
        assertThat(propertySetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePropertySet() throws Exception {
        // Initialize the database
        propertySetRepository.save(propertySet);

        int databaseSizeBeforeDelete = propertySetRepository.findAll().size();

        // Delete the propertySet
        restPropertySetMockMvc.perform(delete("/api/property-sets/{id}", propertySet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PropertySet> propertySetList = propertySetRepository.findAll();
        assertThat(propertySetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
