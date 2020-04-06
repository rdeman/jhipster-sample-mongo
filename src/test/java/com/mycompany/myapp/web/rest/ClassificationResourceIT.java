package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleMongoApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Classification;
import com.mycompany.myapp.repository.ClassificationRepository;
import com.mycompany.myapp.service.ClassificationService;
import com.mycompany.myapp.service.dto.ClassificationDTO;
import com.mycompany.myapp.service.mapper.ClassificationMapper;

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
 * Integration tests for the {@link ClassificationResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterSampleMongoApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class ClassificationResourceIT {

    private static final String DEFAULT_CLASS_ATT_1 = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_ATT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_ATT_2 = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_ATT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_ATT_3 = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_ATT_3 = "BBBBBBBBBB";

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private ClassificationMapper classificationMapper;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private MockMvc restClassificationMockMvc;

    private Classification classification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classification createEntity() {
        Classification classification = new Classification()
            .classAtt1(DEFAULT_CLASS_ATT_1)
            .classAtt2(DEFAULT_CLASS_ATT_2)
            .classAtt3(DEFAULT_CLASS_ATT_3);
        return classification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classification createUpdatedEntity() {
        Classification classification = new Classification()
            .classAtt1(UPDATED_CLASS_ATT_1)
            .classAtt2(UPDATED_CLASS_ATT_2)
            .classAtt3(UPDATED_CLASS_ATT_3);
        return classification;
    }

    @BeforeEach
    public void initTest() {
        classificationRepository.deleteAll();
        classification = createEntity();
    }

    @Test
    public void createClassification() throws Exception {
        int databaseSizeBeforeCreate = classificationRepository.findAll().size();

        // Create the Classification
        ClassificationDTO classificationDTO = classificationMapper.toDto(classification);
        restClassificationMockMvc.perform(post("/api/classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Classification in the database
        List<Classification> classificationList = classificationRepository.findAll();
        assertThat(classificationList).hasSize(databaseSizeBeforeCreate + 1);
        Classification testClassification = classificationList.get(classificationList.size() - 1);
        assertThat(testClassification.getClassAtt1()).isEqualTo(DEFAULT_CLASS_ATT_1);
        assertThat(testClassification.getClassAtt2()).isEqualTo(DEFAULT_CLASS_ATT_2);
        assertThat(testClassification.getClassAtt3()).isEqualTo(DEFAULT_CLASS_ATT_3);
    }

    @Test
    public void createClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificationRepository.findAll().size();

        // Create the Classification with an existing ID
        classification.setId("existing_id");
        ClassificationDTO classificationDTO = classificationMapper.toDto(classification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificationMockMvc.perform(post("/api/classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classification in the database
        List<Classification> classificationList = classificationRepository.findAll();
        assertThat(classificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllClassifications() throws Exception {
        // Initialize the database
        classificationRepository.save(classification);

        // Get all the classificationList
        restClassificationMockMvc.perform(get("/api/classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classification.getId())))
            .andExpect(jsonPath("$.[*].classAtt1").value(hasItem(DEFAULT_CLASS_ATT_1)))
            .andExpect(jsonPath("$.[*].classAtt2").value(hasItem(DEFAULT_CLASS_ATT_2)))
            .andExpect(jsonPath("$.[*].classAtt3").value(hasItem(DEFAULT_CLASS_ATT_3)));
    }
    
    @Test
    public void getClassification() throws Exception {
        // Initialize the database
        classificationRepository.save(classification);

        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", classification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classification.getId()))
            .andExpect(jsonPath("$.classAtt1").value(DEFAULT_CLASS_ATT_1))
            .andExpect(jsonPath("$.classAtt2").value(DEFAULT_CLASS_ATT_2))
            .andExpect(jsonPath("$.classAtt3").value(DEFAULT_CLASS_ATT_3));
    }

    @Test
    public void getNonExistingClassification() throws Exception {
        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClassification() throws Exception {
        // Initialize the database
        classificationRepository.save(classification);

        int databaseSizeBeforeUpdate = classificationRepository.findAll().size();

        // Update the classification
        Classification updatedClassification = classificationRepository.findById(classification.getId()).get();
        updatedClassification
            .classAtt1(UPDATED_CLASS_ATT_1)
            .classAtt2(UPDATED_CLASS_ATT_2)
            .classAtt3(UPDATED_CLASS_ATT_3);
        ClassificationDTO classificationDTO = classificationMapper.toDto(updatedClassification);

        restClassificationMockMvc.perform(put("/api/classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificationDTO)))
            .andExpect(status().isOk());

        // Validate the Classification in the database
        List<Classification> classificationList = classificationRepository.findAll();
        assertThat(classificationList).hasSize(databaseSizeBeforeUpdate);
        Classification testClassification = classificationList.get(classificationList.size() - 1);
        assertThat(testClassification.getClassAtt1()).isEqualTo(UPDATED_CLASS_ATT_1);
        assertThat(testClassification.getClassAtt2()).isEqualTo(UPDATED_CLASS_ATT_2);
        assertThat(testClassification.getClassAtt3()).isEqualTo(UPDATED_CLASS_ATT_3);
    }

    @Test
    public void updateNonExistingClassification() throws Exception {
        int databaseSizeBeforeUpdate = classificationRepository.findAll().size();

        // Create the Classification
        ClassificationDTO classificationDTO = classificationMapper.toDto(classification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificationMockMvc.perform(put("/api/classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classification in the database
        List<Classification> classificationList = classificationRepository.findAll();
        assertThat(classificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteClassification() throws Exception {
        // Initialize the database
        classificationRepository.save(classification);

        int databaseSizeBeforeDelete = classificationRepository.findAll().size();

        // Delete the classification
        restClassificationMockMvc.perform(delete("/api/classifications/{id}", classification.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classification> classificationList = classificationRepository.findAll();
        assertThat(classificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
