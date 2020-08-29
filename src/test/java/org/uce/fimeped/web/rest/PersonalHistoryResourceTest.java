package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.PersonalHistory;
import org.uce.fimeped.repository.PersonalHistoryRepository;

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
 * Test class for the PersonalHistoryResource REST controller.
 *
 * @see PersonalHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonalHistoryResourceTest {


    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_ILLNESS = "SAMPLE_TEXT";
    private static final String UPDATED_ILLNESS = "UPDATED_TEXT";

    @Inject
    private PersonalHistoryRepository personalHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPersonalHistoryMockMvc;

    private PersonalHistory personalHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonalHistoryResource personalHistoryResource = new PersonalHistoryResource();
        ReflectionTestUtils.setField(personalHistoryResource, "personalHistoryRepository", personalHistoryRepository);
        this.restPersonalHistoryMockMvc = MockMvcBuilders.standaloneSetup(personalHistoryResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        personalHistory = new PersonalHistory();
        personalHistory.setDate(DEFAULT_DATE);
        personalHistory.setDescription(DEFAULT_DESCRIPTION);
        personalHistory.setIllness(DEFAULT_ILLNESS);
    }

    @Test
    @Transactional
    public void createPersonalHistory() throws Exception {
        int databaseSizeBeforeCreate = personalHistoryRepository.findAll().size();

        // Create the PersonalHistory

        restPersonalHistoryMockMvc.perform(post("/api/personalHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personalHistory)))
                .andExpect(status().isCreated());

        // Validate the PersonalHistory in the database
        List<PersonalHistory> personalHistorys = personalHistoryRepository.findAll();
        assertThat(personalHistorys).hasSize(databaseSizeBeforeCreate + 1);
        PersonalHistory testPersonalHistory = personalHistorys.get(personalHistorys.size() - 1);
        assertThat(testPersonalHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPersonalHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPersonalHistory.getIllness()).isEqualTo(DEFAULT_ILLNESS);
    }

    @Test
    @Transactional
    public void getAllPersonalHistorys() throws Exception {
        // Initialize the database
        personalHistoryRepository.saveAndFlush(personalHistory);

        // Get all the personalHistorys
        restPersonalHistoryMockMvc.perform(get("/api/personalHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                //.andExpect(jsonPath("$.[*].id").value(hasItem(personalHistory.getId().intValue())))
                //.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                //.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
               // .andExpect(jsonPath("$.[*].illness").value(hasItem(DEFAULT_ILLNESS.toString())));
    }

    @Test
    @Transactional
    public void getPersonalHistory() throws Exception {
        // Initialize the database
        personalHistoryRepository.saveAndFlush(personalHistory);

        // Get the personalHistory
        restPersonalHistoryMockMvc.perform(get("/api/personalHistorys/{id}", personalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(personalHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.illness").value(DEFAULT_ILLNESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalHistory() throws Exception {
        // Get the personalHistory
        restPersonalHistoryMockMvc.perform(get("/api/personalHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalHistory() throws Exception {
        // Initialize the database
        personalHistoryRepository.saveAndFlush(personalHistory);

		int databaseSizeBeforeUpdate = personalHistoryRepository.findAll().size();

        // Update the personalHistory
        personalHistory.setDate(UPDATED_DATE);
        personalHistory.setDescription(UPDATED_DESCRIPTION);
        personalHistory.setIllness(UPDATED_ILLNESS);
        

        restPersonalHistoryMockMvc.perform(put("/api/personalHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personalHistory)))
                .andExpect(status().isOk());

        // Validate the PersonalHistory in the database
        List<PersonalHistory> personalHistorys = personalHistoryRepository.findAll();
        assertThat(personalHistorys).hasSize(databaseSizeBeforeUpdate);
        PersonalHistory testPersonalHistory = personalHistorys.get(personalHistorys.size() - 1);
        assertThat(testPersonalHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPersonalHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPersonalHistory.getIllness()).isEqualTo(UPDATED_ILLNESS);
    }

    @Test
    @Transactional
    public void deletePersonalHistory() throws Exception {
        // Initialize the database
        personalHistoryRepository.saveAndFlush(personalHistory);

		int databaseSizeBeforeDelete = personalHistoryRepository.findAll().size();

        // Get the personalHistory
        restPersonalHistoryMockMvc.perform(delete("/api/personalHistorys/{id}", personalHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonalHistory> personalHistorys = personalHistoryRepository.findAll();
        assertThat(personalHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
