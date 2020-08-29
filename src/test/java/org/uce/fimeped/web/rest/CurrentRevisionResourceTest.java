package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.CurrentRevision;
import org.uce.fimeped.repository.CurrentRevisionRepository;

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
 * Test class for the CurrentRevisionResource REST controller.
 *
 * @see CurrentRevisionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CurrentRevisionResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_ORGAN = "SAMPLE_TEXT";
    private static final String UPDATED_ORGAN = "UPDATED_TEXT";
    private static final String DEFAULT_WE_NE = "SAMPLE_TEXT";
    private static final String UPDATED_WE_NE = "UPDATED_TEXT";

    @Inject
    private CurrentRevisionRepository currentRevisionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restCurrentRevisionMockMvc;

    private CurrentRevision currentRevision;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurrentRevisionResource currentRevisionResource = new CurrentRevisionResource();
        ReflectionTestUtils.setField(currentRevisionResource, "currentRevisionRepository", currentRevisionRepository);
        this.restCurrentRevisionMockMvc = MockMvcBuilders.standaloneSetup(currentRevisionResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        currentRevision = new CurrentRevision();
        currentRevision.setDescription(DEFAULT_DESCRIPTION);
        currentRevision.setOrgan(DEFAULT_ORGAN);
        currentRevision.setWeNe(DEFAULT_WE_NE);
    }

    @Test
    @Transactional
    public void createCurrentRevision() throws Exception {
        int databaseSizeBeforeCreate = currentRevisionRepository.findAll().size();

        // Create the CurrentRevision

        restCurrentRevisionMockMvc.perform(post("/api/currentRevisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currentRevision)))
                .andExpect(status().isCreated());

        // Validate the CurrentRevision in the database
        List<CurrentRevision> currentRevisions = currentRevisionRepository.findAll();
        assertThat(currentRevisions).hasSize(databaseSizeBeforeCreate + 1);
        CurrentRevision testCurrentRevision = currentRevisions.get(currentRevisions.size() - 1);
        assertThat(testCurrentRevision.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurrentRevision.getOrgan()).isEqualTo(DEFAULT_ORGAN);
        assertThat(testCurrentRevision.getWeNe()).isEqualTo(DEFAULT_WE_NE);
    }

    @Test
    @Transactional
    public void getAllCurrentRevisions() throws Exception {
        // Initialize the database
        currentRevisionRepository.saveAndFlush(currentRevision);

        // Get all the currentRevisions
        restCurrentRevisionMockMvc.perform(get("/api/currentRevisions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(currentRevision.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].organ").value(hasItem(DEFAULT_ORGAN.toString())))
                .andExpect(jsonPath("$.[*].weNe").value(hasItem(DEFAULT_WE_NE.toString())));
    }

    @Test
    @Transactional
    public void getCurrentRevision() throws Exception {
        // Initialize the database
        currentRevisionRepository.saveAndFlush(currentRevision);

        // Get the currentRevision
        restCurrentRevisionMockMvc.perform(get("/api/currentRevisions/{id}", currentRevision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(currentRevision.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.organ").value(DEFAULT_ORGAN.toString()))
            .andExpect(jsonPath("$.weNe").value(DEFAULT_WE_NE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrentRevision() throws Exception {
        // Get the currentRevision
        restCurrentRevisionMockMvc.perform(get("/api/currentRevisions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentRevision() throws Exception {
        // Initialize the database
        currentRevisionRepository.saveAndFlush(currentRevision);

		int databaseSizeBeforeUpdate = currentRevisionRepository.findAll().size();

        // Update the currentRevision
        currentRevision.setDescription(UPDATED_DESCRIPTION);
        currentRevision.setOrgan(UPDATED_ORGAN);
        currentRevision.setWeNe(UPDATED_WE_NE);
        

        restCurrentRevisionMockMvc.perform(put("/api/currentRevisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currentRevision)))
                .andExpect(status().isOk());

        // Validate the CurrentRevision in the database
        List<CurrentRevision> currentRevisions = currentRevisionRepository.findAll();
        assertThat(currentRevisions).hasSize(databaseSizeBeforeUpdate);
        CurrentRevision testCurrentRevision = currentRevisions.get(currentRevisions.size() - 1);
        assertThat(testCurrentRevision.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurrentRevision.getOrgan()).isEqualTo(UPDATED_ORGAN);
        assertThat(testCurrentRevision.getWeNe()).isEqualTo(UPDATED_WE_NE);
    }

    @Test
    @Transactional
    public void deleteCurrentRevision() throws Exception {
        // Initialize the database
        currentRevisionRepository.saveAndFlush(currentRevision);

		int databaseSizeBeforeDelete = currentRevisionRepository.findAll().size();

        // Get the currentRevision
        restCurrentRevisionMockMvc.perform(delete("/api/currentRevisions/{id}", currentRevision.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrentRevision> currentRevisions = currentRevisionRepository.findAll();
        assertThat(currentRevisions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
