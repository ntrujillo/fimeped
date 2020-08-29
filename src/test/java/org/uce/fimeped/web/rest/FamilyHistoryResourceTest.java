package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.FamilyHistory;
import org.uce.fimeped.repository.FamilyHistoryRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FamilyHistoryResource REST controller.
 *
 * @see FamilyHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FamilyHistoryResourceTest {


    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_ILLNESS = "SAMPLE_TEXT";
    private static final String UPDATED_ILLNESS = "UPDATED_TEXT";

    @Inject
    private FamilyHistoryRepository familyHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restFamilyHistoryMockMvc;

    private FamilyHistory familyHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FamilyHistoryResource familyHistoryResource = new FamilyHistoryResource();
        ReflectionTestUtils.setField(familyHistoryResource, "familyHistoryRepository", familyHistoryRepository);
        this.restFamilyHistoryMockMvc = MockMvcBuilders.standaloneSetup(familyHistoryResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        familyHistory = new FamilyHistory();
        familyHistory.setDate(DEFAULT_DATE);
        familyHistory.setDescription(DEFAULT_DESCRIPTION);
        familyHistory.setIllness(DEFAULT_ILLNESS);
    }

    @Test
    @Transactional
    public void createFamilyHistory() throws Exception {
        int databaseSizeBeforeCreate = familyHistoryRepository.findAll().size();

        // Create the FamilyHistory

        restFamilyHistoryMockMvc.perform(post("/api/familyHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(familyHistory)))
                .andExpect(status().isCreated());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistorys = familyHistoryRepository.findAll();
        assertThat(familyHistorys).hasSize(databaseSizeBeforeCreate + 1);
        FamilyHistory testFamilyHistory = familyHistorys.get(familyHistorys.size() - 1);
        assertThat(testFamilyHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFamilyHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFamilyHistory.getIllness()).isEqualTo(DEFAULT_ILLNESS);
    }

    @Test
    @Transactional
    public void getAllFamilyHistorys() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

        // Get all the familyHistorys
        restFamilyHistoryMockMvc.perform(get("/api/familyHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                //.andExpect(jsonPath("$.[*].id").value(hasItem(familyHistory.getId().intValue())))
              //  .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
               // .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
              //  .andExpect(jsonPath("$.[*].illness").value(hasItem(DEFAULT_ILLNESS.toString())));
    }

    @Test
    @Transactional
    public void getFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(get("/api/familyHistorys/{id}", familyHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(familyHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.illness").value(DEFAULT_ILLNESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyHistory() throws Exception {
        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(get("/api/familyHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

		int databaseSizeBeforeUpdate = familyHistoryRepository.findAll().size();

        // Update the familyHistory
        familyHistory.setDate(UPDATED_DATE);
        familyHistory.setDescription(UPDATED_DESCRIPTION);
        familyHistory.setIllness(UPDATED_ILLNESS);
        

        restFamilyHistoryMockMvc.perform(put("/api/familyHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(familyHistory)))
                .andExpect(status().isOk());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistorys = familyHistoryRepository.findAll();
        assertThat(familyHistorys).hasSize(databaseSizeBeforeUpdate);
        FamilyHistory testFamilyHistory = familyHistorys.get(familyHistorys.size() - 1);
        assertThat(testFamilyHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFamilyHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFamilyHistory.getIllness()).isEqualTo(UPDATED_ILLNESS);
    }

    @Test
    @Transactional
    public void deleteFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

		int databaseSizeBeforeDelete = familyHistoryRepository.findAll().size();

        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(delete("/api/familyHistorys/{id}", familyHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FamilyHistory> familyHistorys = familyHistoryRepository.findAll();
        assertThat(familyHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
