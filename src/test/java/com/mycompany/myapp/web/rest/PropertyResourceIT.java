package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleMongoApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Property;
import com.mycompany.myapp.repository.PropertyRepository;
import com.mycompany.myapp.service.PropertyService;
import com.mycompany.myapp.service.dto.PropertyDTO;
import com.mycompany.myapp.service.mapper.PropertyMapper;

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
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterSampleMongoApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class PropertyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private MockMvc restPropertyMockMvc;

    private Property property;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity() {
        Property property = new Property()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return property;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity() {
        Property property = new Property()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return property;
    }

    @BeforeEach
    public void initTest() {
        propertyRepository.deleteAll();
        property = createEntity();
    }

    @Test
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);
        restPropertyMockMvc.perform(post("/api/properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId("existing_id");
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc.perform(post("/api/properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.save(property);

        // Get all the propertyList
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.save(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.save(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).get();
        updatedProperty
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        PropertyDTO propertyDTO = propertyMapper.toDto(updatedProperty);

        restPropertyMockMvc.perform(put("/api/properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc.perform(put("/api/properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.save(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Delete the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
