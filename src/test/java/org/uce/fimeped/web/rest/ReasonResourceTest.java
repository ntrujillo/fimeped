package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.Reason;
import org.uce.fimeped.repository.ReasonRepository;

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
 * Test class for the ReasonResource REST controller.
 *
 * @see ReasonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReasonResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private ReasonRepository reasonRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restReasonMockMvc;

    private Reason reason;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReasonResource reasonResource = new ReasonResource();
        ReflectionTestUtils.setField(reasonResource, "reasonRepository", reasonRepository);
        this.restReasonMockMvc = MockMvcBuilders.standaloneSetup(reasonResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reason = new Reason();
        reason.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReason() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason

        restReasonMockMvc.perform(post("/api/reasons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reason)))
                .andExpect(status().isCreated());

        // Validate the Reason in the database
        List<Reason> reasons = reasonRepository.findAll();
        assertThat(reasons).hasSize(databaseSizeBeforeCreate + 1);
        Reason testReason = reasons.get(reasons.size() - 1);
        assertThat(testReason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReasons() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasons
        restReasonMockMvc.perform(get("/api/reasons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reason.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReason() throws Exception {
        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

		int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Update the reason
        reason.setDescription(UPDATED_DESCRIPTION);
        

        restReasonMockMvc.perform(put("/api/reasons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reason)))
                .andExpect(status().isOk());

        // Validate the Reason in the database
        List<Reason> reasons = reasonRepository.findAll();
        assertThat(reasons).hasSize(databaseSizeBeforeUpdate);
        Reason testReason = reasons.get(reasons.size() - 1);
        assertThat(testReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

		int databaseSizeBeforeDelete = reasonRepository.findAll().size();

        // Get the reason
        restReasonMockMvc.perform(delete("/api/reasons/{id}", reason.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reason> reasons = reasonRepository.findAll();
        assertThat(reasons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
