package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.CurrentIllness;
import org.uce.fimeped.repository.CurrentIllnessRepository;

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
 * Test class for the CurrentIllnessResource REST controller.
 *
 * @see CurrentIllnessResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CurrentIllnessResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private CurrentIllnessRepository currentIllnessRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restCurrentIllnessMockMvc;

    private CurrentIllness currentIllness;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurrentIllnessResource currentIllnessResource = new CurrentIllnessResource();
        ReflectionTestUtils.setField(currentIllnessResource, "currentIllnessRepository", currentIllnessRepository);
        this.restCurrentIllnessMockMvc = MockMvcBuilders.standaloneSetup(currentIllnessResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        currentIllness = new CurrentIllness();
        currentIllness.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCurrentIllness() throws Exception {
        int databaseSizeBeforeCreate = currentIllnessRepository.findAll().size();

        // Create the CurrentIllness

        restCurrentIllnessMockMvc.perform(post("/api/currentIllnesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currentIllness)))
                .andExpect(status().isCreated());

        // Validate the CurrentIllness in the database
        List<CurrentIllness> currentIllnesss = currentIllnessRepository.findAll();
        assertThat(currentIllnesss).hasSize(databaseSizeBeforeCreate + 1);
        CurrentIllness testCurrentIllness = currentIllnesss.get(currentIllnesss.size() - 1);
        assertThat(testCurrentIllness.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrentIllnesss() throws Exception {
        // Initialize the database
        currentIllnessRepository.saveAndFlush(currentIllness);

        // Get all the currentIllnesss
        restCurrentIllnessMockMvc.perform(get("/api/currentIllnesss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(currentIllness.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCurrentIllness() throws Exception {
        // Initialize the database
        currentIllnessRepository.saveAndFlush(currentIllness);

        // Get the currentIllness
        restCurrentIllnessMockMvc.perform(get("/api/currentIllnesss/{id}", currentIllness.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(currentIllness.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrentIllness() throws Exception {
        // Get the currentIllness
        restCurrentIllnessMockMvc.perform(get("/api/currentIllnesss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentIllness() throws Exception {
        // Initialize the database
        currentIllnessRepository.saveAndFlush(currentIllness);

		int databaseSizeBeforeUpdate = currentIllnessRepository.findAll().size();

        // Update the currentIllness
        currentIllness.setDescription(UPDATED_DESCRIPTION);
        

        restCurrentIllnessMockMvc.perform(put("/api/currentIllnesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currentIllness)))
                .andExpect(status().isOk());

        // Validate the CurrentIllness in the database
        List<CurrentIllness> currentIllnesss = currentIllnessRepository.findAll();
        assertThat(currentIllnesss).hasSize(databaseSizeBeforeUpdate);
        CurrentIllness testCurrentIllness = currentIllnesss.get(currentIllnesss.size() - 1);
        assertThat(testCurrentIllness.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCurrentIllness() throws Exception {
        // Initialize the database
        currentIllnessRepository.saveAndFlush(currentIllness);

		int databaseSizeBeforeDelete = currentIllnessRepository.findAll().size();

        // Get the currentIllness
        restCurrentIllnessMockMvc.perform(delete("/api/currentIllnesss/{id}", currentIllness.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrentIllness> currentIllnesss = currentIllnessRepository.findAll();
        assertThat(currentIllnesss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
