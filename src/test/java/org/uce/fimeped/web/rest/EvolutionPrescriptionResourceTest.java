package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.EvolutionPrescription;
import org.uce.fimeped.repository.EvolutionPrescriptionRepository;

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
 * Test class for the EvolutionPrescriptionResource REST controller.
 *
 * @see EvolutionPrescriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EvolutionPrescriptionResourceTest {

    private static final String DEFAULT_EVOLUTION = "SAMPLE_TEXT";
    private static final String UPDATED_EVOLUTION = "UPDATED_TEXT";
    private static final String DEFAULT_PRESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_PRESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_MEDICINES = "SAMPLE_TEXT";
    private static final String UPDATED_MEDICINES = "UPDATED_TEXT";

    @Inject
    private EvolutionPrescriptionRepository evolutionPrescriptionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restEvolutionPrescriptionMockMvc;

    private EvolutionPrescription evolutionPrescription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvolutionPrescriptionResource evolutionPrescriptionResource = new EvolutionPrescriptionResource();
        ReflectionTestUtils.setField(evolutionPrescriptionResource, "evolutionPrescriptionRepository", evolutionPrescriptionRepository);
        this.restEvolutionPrescriptionMockMvc = MockMvcBuilders.standaloneSetup(evolutionPrescriptionResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        evolutionPrescription = new EvolutionPrescription();
        evolutionPrescription.setEvolution(DEFAULT_EVOLUTION);
        evolutionPrescription.setPrescription(DEFAULT_PRESCRIPTION);
        evolutionPrescription.setMedicines(DEFAULT_MEDICINES);
    }

    @Test
    @Transactional
    public void createEvolutionPrescription() throws Exception {
        int databaseSizeBeforeCreate = evolutionPrescriptionRepository.findAll().size();

        // Create the EvolutionPrescription

        restEvolutionPrescriptionMockMvc.perform(post("/api/evolutionPrescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evolutionPrescription)))
                .andExpect(status().isCreated());

        // Validate the EvolutionPrescription in the database
        List<EvolutionPrescription> evolutionPrescriptions = evolutionPrescriptionRepository.findAll();
        assertThat(evolutionPrescriptions).hasSize(databaseSizeBeforeCreate + 1);
        EvolutionPrescription testEvolutionPrescription = evolutionPrescriptions.get(evolutionPrescriptions.size() - 1);
        assertThat(testEvolutionPrescription.getEvolution()).isEqualTo(DEFAULT_EVOLUTION);
        assertThat(testEvolutionPrescription.getPrescription()).isEqualTo(DEFAULT_PRESCRIPTION);
        assertThat(testEvolutionPrescription.getMedicines()).isEqualTo(DEFAULT_MEDICINES);
    }

    @Test
    @Transactional
    public void getAllEvolutionPrescriptions() throws Exception {
        // Initialize the database
        evolutionPrescriptionRepository.saveAndFlush(evolutionPrescription);

        // Get all the evolutionPrescriptions
        restEvolutionPrescriptionMockMvc.perform(get("/api/evolutionPrescriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evolutionPrescription.getId().intValue())))
                .andExpect(jsonPath("$.[*].evolution").value(hasItem(DEFAULT_EVOLUTION.toString())))
                .andExpect(jsonPath("$.[*].prescription").value(hasItem(DEFAULT_PRESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].medicines").value(hasItem(DEFAULT_MEDICINES.toString())));
    }

    @Test
    @Transactional
    public void getEvolutionPrescription() throws Exception {
        // Initialize the database
        evolutionPrescriptionRepository.saveAndFlush(evolutionPrescription);

        // Get the evolutionPrescription
        restEvolutionPrescriptionMockMvc.perform(get("/api/evolutionPrescriptions/{id}", evolutionPrescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(evolutionPrescription.getId().intValue()))
            .andExpect(jsonPath("$.evolution").value(DEFAULT_EVOLUTION.toString()))
            .andExpect(jsonPath("$.prescription").value(DEFAULT_PRESCRIPTION.toString()))
            .andExpect(jsonPath("$.medicines").value(DEFAULT_MEDICINES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvolutionPrescription() throws Exception {
        // Get the evolutionPrescription
        restEvolutionPrescriptionMockMvc.perform(get("/api/evolutionPrescriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvolutionPrescription() throws Exception {
        // Initialize the database
        evolutionPrescriptionRepository.saveAndFlush(evolutionPrescription);

		int databaseSizeBeforeUpdate = evolutionPrescriptionRepository.findAll().size();

        // Update the evolutionPrescription
        evolutionPrescription.setEvolution(UPDATED_EVOLUTION);
        evolutionPrescription.setPrescription(UPDATED_PRESCRIPTION);
        evolutionPrescription.setMedicines(UPDATED_MEDICINES);
        

        restEvolutionPrescriptionMockMvc.perform(put("/api/evolutionPrescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evolutionPrescription)))
                .andExpect(status().isOk());

        // Validate the EvolutionPrescription in the database
        List<EvolutionPrescription> evolutionPrescriptions = evolutionPrescriptionRepository.findAll();
        assertThat(evolutionPrescriptions).hasSize(databaseSizeBeforeUpdate);
        EvolutionPrescription testEvolutionPrescription = evolutionPrescriptions.get(evolutionPrescriptions.size() - 1);
        assertThat(testEvolutionPrescription.getEvolution()).isEqualTo(UPDATED_EVOLUTION);
        assertThat(testEvolutionPrescription.getPrescription()).isEqualTo(UPDATED_PRESCRIPTION);
        assertThat(testEvolutionPrescription.getMedicines()).isEqualTo(UPDATED_MEDICINES);
    }

    @Test
    @Transactional
    public void deleteEvolutionPrescription() throws Exception {
        // Initialize the database
        evolutionPrescriptionRepository.saveAndFlush(evolutionPrescription);

		int databaseSizeBeforeDelete = evolutionPrescriptionRepository.findAll().size();

        // Get the evolutionPrescription
        restEvolutionPrescriptionMockMvc.perform(delete("/api/evolutionPrescriptions/{id}", evolutionPrescription.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EvolutionPrescription> evolutionPrescriptions = evolutionPrescriptionRepository.findAll();
        assertThat(evolutionPrescriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
