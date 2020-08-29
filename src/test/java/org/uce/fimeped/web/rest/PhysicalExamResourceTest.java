package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.PhysicalExam;
import org.uce.fimeped.repository.PhysicalExamRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PhysicalExamResource REST controller.
 *
 * @see PhysicalExamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhysicalExamResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_BODY_PART = "SAMPLE_TEXT";
    private static final String UPDATED_BODY_PART = "UPDATED_TEXT";
    private static final String DEFAULT_WE_NE = "SAMPLE_TEXT";
    private static final String UPDATED_WE_NE = "UPDATED_TEXT";

    @Inject
    private PhysicalExamRepository physicalExamRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPhysicalExamMockMvc;

    private PhysicalExam physicalExam;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhysicalExamResource physicalExamResource = new PhysicalExamResource();
        ReflectionTestUtils.setField(physicalExamResource, "physicalExamRepository", physicalExamRepository);
        this.restPhysicalExamMockMvc = MockMvcBuilders.standaloneSetup(physicalExamResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        physicalExam = new PhysicalExam();
        physicalExam.setDescription(DEFAULT_DESCRIPTION);
        physicalExam.setBodyPart(DEFAULT_BODY_PART);
        physicalExam.setWeNe(DEFAULT_WE_NE);
    }

    @Test
    @Transactional
    public void createPhysicalExam() throws Exception {
        int databaseSizeBeforeCreate = physicalExamRepository.findAll().size();

        // Create the PhysicalExam

        restPhysicalExamMockMvc.perform(post("/api/physicalExams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(physicalExam)))
                .andExpect(status().isCreated());

        // Validate the PhysicalExam in the database
        List<PhysicalExam> physicalExams = physicalExamRepository.findAll();
        assertThat(physicalExams).hasSize(databaseSizeBeforeCreate + 1);
        PhysicalExam testPhysicalExam = physicalExams.get(physicalExams.size() - 1);
        assertThat(testPhysicalExam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhysicalExam.getBodyPart()).isEqualTo(DEFAULT_BODY_PART);
        assertThat(testPhysicalExam.getWeNe()).isEqualTo(DEFAULT_WE_NE);
    }

    @Test
    @Transactional
    public void getAllPhysicalExams() throws Exception {
        // Initialize the database
        physicalExamRepository.saveAndFlush(physicalExam);

        // Get all the physicalExams
        restPhysicalExamMockMvc.perform(get("/api/physicalExams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(physicalExam.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].bodyPart").value(hasItem(DEFAULT_BODY_PART.toString())))
                .andExpect(jsonPath("$.[*].weNe").value(hasItem(DEFAULT_WE_NE.toString())));
    }

    @Test
    @Transactional
    public void getPhysicalExam() throws Exception {
        // Initialize the database
        physicalExamRepository.saveAndFlush(physicalExam);

        // Get the physicalExam
        restPhysicalExamMockMvc.perform(get("/api/physicalExams/{id}", physicalExam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(physicalExam.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.bodyPart").value(DEFAULT_BODY_PART.toString()))
            .andExpect(jsonPath("$.weNe").value(DEFAULT_WE_NE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhysicalExam() throws Exception {
        // Get the physicalExam
        restPhysicalExamMockMvc.perform(get("/api/physicalExams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhysicalExam() throws Exception {
        // Initialize the database
        physicalExamRepository.saveAndFlush(physicalExam);

		int databaseSizeBeforeUpdate = physicalExamRepository.findAll().size();

        // Update the physicalExam
        physicalExam.setDescription(UPDATED_DESCRIPTION);
        physicalExam.setBodyPart(UPDATED_BODY_PART);
        physicalExam.setWeNe(UPDATED_WE_NE);
        

        restPhysicalExamMockMvc.perform(put("/api/physicalExams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(physicalExam)))
                .andExpect(status().isOk());

        // Validate the PhysicalExam in the database
        List<PhysicalExam> physicalExams = physicalExamRepository.findAll();
        assertThat(physicalExams).hasSize(databaseSizeBeforeUpdate);
        PhysicalExam testPhysicalExam = physicalExams.get(physicalExams.size() - 1);
        assertThat(testPhysicalExam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhysicalExam.getBodyPart()).isEqualTo(UPDATED_BODY_PART);
        assertThat(testPhysicalExam.getWeNe()).isEqualTo(UPDATED_WE_NE);
    }

    @Test
    @Transactional
    public void deletePhysicalExam() throws Exception {
        // Initialize the database
        physicalExamRepository.saveAndFlush(physicalExam);

		int databaseSizeBeforeDelete = physicalExamRepository.findAll().size();

        // Get the physicalExam
        restPhysicalExamMockMvc.perform(delete("/api/physicalExams/{id}", physicalExam.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PhysicalExam> physicalExams = physicalExamRepository.findAll();
        assertThat(physicalExams).hasSize(databaseSizeBeforeDelete - 1);
    }
}
