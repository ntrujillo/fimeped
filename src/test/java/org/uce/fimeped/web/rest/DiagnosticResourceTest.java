package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.Diagnostic;
import org.uce.fimeped.repository.DiagnosticRepository;

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
 * Test class for the DiagnosticResource REST controller.
 *
 * @see DiagnosticResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DiagnosticResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_CIE = "SAMPLE_TEXT";
    private static final String UPDATED_CIE = "UPDATED_TEXT";
    private static final String DEFAULT_PRE_DEF = "SAMPLE_TEXT";
    private static final String UPDATED_PRE_DEF = "UPDATED_TEXT";

    @Inject
    private DiagnosticRepository diagnosticRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restDiagnosticMockMvc;

    private Diagnostic diagnostic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiagnosticResource diagnosticResource = new DiagnosticResource();
        ReflectionTestUtils.setField(diagnosticResource, "diagnosticRepository", diagnosticRepository);
        this.restDiagnosticMockMvc = MockMvcBuilders.standaloneSetup(diagnosticResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        diagnostic = new Diagnostic();
        diagnostic.setDescription(DEFAULT_DESCRIPTION);
        diagnostic.setCie(DEFAULT_CIE);
        diagnostic.setPreDef(DEFAULT_PRE_DEF);
    }

    @Test
    @Transactional
    public void createDiagnostic() throws Exception {
        int databaseSizeBeforeCreate = diagnosticRepository.findAll().size();

        // Create the Diagnostic

        restDiagnosticMockMvc.perform(post("/api/diagnostics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(diagnostic)))
                .andExpect(status().isCreated());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnostics = diagnosticRepository.findAll();
        assertThat(diagnostics).hasSize(databaseSizeBeforeCreate + 1);
        Diagnostic testDiagnostic = diagnostics.get(diagnostics.size() - 1);
        assertThat(testDiagnostic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiagnostic.getCie()).isEqualTo(DEFAULT_CIE);
        assertThat(testDiagnostic.getPreDef()).isEqualTo(DEFAULT_PRE_DEF);
    }

    @Test
    @Transactional
    public void getAllDiagnostics() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnostics
        restDiagnosticMockMvc.perform(get("/api/diagnostics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(diagnostic.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cie").value(hasItem(DEFAULT_CIE.toString())))
                .andExpect(jsonPath("$.[*].preDef").value(hasItem(DEFAULT_PRE_DEF.toString())));
    }

    @Test
    @Transactional
    public void getDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get the diagnostic
        restDiagnosticMockMvc.perform(get("/api/diagnostics/{id}", diagnostic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(diagnostic.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cie").value(DEFAULT_CIE.toString()))
            .andExpect(jsonPath("$.preDef").value(DEFAULT_PRE_DEF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiagnostic() throws Exception {
        // Get the diagnostic
        restDiagnosticMockMvc.perform(get("/api/diagnostics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

		int databaseSizeBeforeUpdate = diagnosticRepository.findAll().size();

        // Update the diagnostic
        diagnostic.setDescription(UPDATED_DESCRIPTION);
        diagnostic.setCie(UPDATED_CIE);
        diagnostic.setPreDef(UPDATED_PRE_DEF);
        

        restDiagnosticMockMvc.perform(put("/api/diagnostics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(diagnostic)))
                .andExpect(status().isOk());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnostics = diagnosticRepository.findAll();
        assertThat(diagnostics).hasSize(databaseSizeBeforeUpdate);
        Diagnostic testDiagnostic = diagnostics.get(diagnostics.size() - 1);
        assertThat(testDiagnostic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiagnostic.getCie()).isEqualTo(UPDATED_CIE);
        assertThat(testDiagnostic.getPreDef()).isEqualTo(UPDATED_PRE_DEF);
    }

    @Test
    @Transactional
    public void deleteDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

		int databaseSizeBeforeDelete = diagnosticRepository.findAll().size();

        // Get the diagnostic
        restDiagnosticMockMvc.perform(delete("/api/diagnostics/{id}", diagnostic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Diagnostic> diagnostics = diagnosticRepository.findAll();
        assertThat(diagnostics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
