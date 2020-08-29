package org.uce.fimeped.web.rest;



import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.uce.fimeped.Application;
import org.uce.fimeped.domain.ClinicHistoryAddInf;
import org.uce.fimeped.repository.ClinicHistoryAddInfRepository;
import org.uce.fimeped.web.rest.ClinicHistoryAddInfResource;



/**
 * Test class for the ClinicHistoryAddInfResource REST controller.
 *
 * @see ClinicHistoryAddInfResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClinicHistoryAddInfResourceTest {


    private static final Integer DEFAULT_MENARCHE_AGE = 1;
    private static final Integer UPDATED_MENARCHE_AGE = 2;

    private static final Integer DEFAULT_MENOPAUSE_AGE = 1;
    private static final Integer UPDATED_MENOPAUSE_AGE = 2;

    private static final Integer DEFAULT_CYCLES = 1;
    private static final Integer UPDATED_CYCLES = 2;
    private static final String DEFAULT_SEXUALLY_ACTIVE = "SAMPLE_TEXT";
    private static final String UPDATED_SEXUALLY_ACTIVE = "UPDATED_TEXT";

    private static final Integer DEFAULT_FEAT = 1;
    private static final Integer UPDATED_FEAT = 2;

    private static final Integer DEFAULT_DELIVERIES = 1;
    private static final Integer UPDATED_DELIVERIES = 2;

    private static final Integer DEFAULT_ABORTIONS = 1;
    private static final Integer UPDATED_ABORTIONS = 2;

    private static final Integer DEFAULT_CAESAREANS = 1;
    private static final Integer UPDATED_CAESAREANS = 2;

    private static final Integer DEFAULT_LIVING_CHILDREN = 1;
    private static final Integer UPDATED_LIVING_CHILDREN = 2;

    private static final LocalDate DEFAULT_LAST_MENARCHE = new LocalDate(0L);
    private static final LocalDate UPDATED_LAST_MENARCHE = new LocalDate();

    private static final LocalDate DEFAULT_LAST_DELIVERY = new LocalDate(0L);
    private static final LocalDate UPDATED_LAST_DELIVERY = new LocalDate();

    private static final LocalDate DEFAULT_LAST_CITOLOGY = new LocalDate(0L);
    private static final LocalDate UPDATED_LAST_CITOLOGY = new LocalDate();
    private static final String DEFAULT_BIOPSY = "SAMPLE_TEXT";
    private static final String UPDATED_BIOPSY = "UPDATED_TEXT";
    private static final String DEFAULT_PROTECTION_METHOD = "SAMPLE_TEXT";
    private static final String UPDATED_PROTECTION_METHOD = "UPDATED_TEXT";
    private static final String DEFAULT_HORMONE_TERAPY = "SAMPLE_TEXT";
    private static final String UPDATED_HORMONE_TERAPY = "UPDATED_TEXT";
    private static final String DEFAULT_COLPOSCOPY = "SAMPLE_TEXT";
    private static final String UPDATED_COLPOSCOPY = "UPDATED_TEXT";
    private static final String DEFAULT_MAMMOGRAFHY = "SAMPLE_TEXT";
    private static final String UPDATED_MAMMOGRAFHY = "UPDATED_TEXT";
   

    @Inject
    private ClinicHistoryAddInfRepository clinicHistoryAddInfRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restClinicHistoryAddInfMockMvc;

    private ClinicHistoryAddInf clinicHistoryAddInf;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicHistoryAddInfResource clinicHistoryAddInfResource = new ClinicHistoryAddInfResource();
        ReflectionTestUtils.setField(clinicHistoryAddInfResource, "clinicHistoryAddInfRepository", clinicHistoryAddInfRepository);
        this.restClinicHistoryAddInfMockMvc = MockMvcBuilders.standaloneSetup(clinicHistoryAddInfResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        clinicHistoryAddInf = new ClinicHistoryAddInf();
        clinicHistoryAddInf.setMenarcheAge(DEFAULT_MENARCHE_AGE);
        clinicHistoryAddInf.setMenopauseAge(DEFAULT_MENOPAUSE_AGE);
        clinicHistoryAddInf.setCycles(DEFAULT_CYCLES);
        clinicHistoryAddInf.setSexuallyActive(DEFAULT_SEXUALLY_ACTIVE);
        clinicHistoryAddInf.setFeat(DEFAULT_FEAT);
        clinicHistoryAddInf.setDeliveries(DEFAULT_DELIVERIES);
        clinicHistoryAddInf.setAbortions(DEFAULT_ABORTIONS);
        clinicHistoryAddInf.setCaesareans(DEFAULT_CAESAREANS);
        clinicHistoryAddInf.setLivingChildren(DEFAULT_LIVING_CHILDREN);
        clinicHistoryAddInf.setLastMenarche(DEFAULT_LAST_MENARCHE);
        clinicHistoryAddInf.setLastDelivery(DEFAULT_LAST_DELIVERY);
        clinicHistoryAddInf.setLastCitology(DEFAULT_LAST_CITOLOGY);
        clinicHistoryAddInf.setBiopsy(DEFAULT_BIOPSY);
        clinicHistoryAddInf.setProtectionMethod(DEFAULT_PROTECTION_METHOD);
        clinicHistoryAddInf.setHormoneTerapy(DEFAULT_HORMONE_TERAPY);
        clinicHistoryAddInf.setColposcopy(DEFAULT_COLPOSCOPY);
        clinicHistoryAddInf.setMammografhy(DEFAULT_MAMMOGRAFHY);      
    }

    @Test
    @Transactional
    public void createClinicHistoryAddInf() throws Exception {
        int databaseSizeBeforeCreate = clinicHistoryAddInfRepository.findAll().size();

        // Create the ClinicHistoryAddInf

        restClinicHistoryAddInfMockMvc.perform(post("/api/clinicHistoryAddInfs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clinicHistoryAddInf)))
                .andExpect(status().isCreated());

        // Validate the ClinicHistoryAddInf in the database
        List<ClinicHistoryAddInf> clinicHistoryAddInfs = clinicHistoryAddInfRepository.findAll();
        assertThat(clinicHistoryAddInfs).hasSize(databaseSizeBeforeCreate + 1);
        ClinicHistoryAddInf testClinicHistoryAddInf = clinicHistoryAddInfs.get(clinicHistoryAddInfs.size() - 1);
        assertThat(testClinicHistoryAddInf.getMenarcheAge()).isEqualTo(DEFAULT_MENARCHE_AGE);
        assertThat(testClinicHistoryAddInf.getMenopauseAge()).isEqualTo(DEFAULT_MENOPAUSE_AGE);
        assertThat(testClinicHistoryAddInf.getCycles()).isEqualTo(DEFAULT_CYCLES);
        assertThat(testClinicHistoryAddInf.getSexuallyActive()).isEqualTo(DEFAULT_SEXUALLY_ACTIVE);
        assertThat(testClinicHistoryAddInf.getFeat()).isEqualTo(DEFAULT_FEAT);
        assertThat(testClinicHistoryAddInf.getDeliveries()).isEqualTo(DEFAULT_DELIVERIES);
        assertThat(testClinicHistoryAddInf.getAbortions()).isEqualTo(DEFAULT_ABORTIONS);
        assertThat(testClinicHistoryAddInf.getCaesareans()).isEqualTo(DEFAULT_CAESAREANS);
        assertThat(testClinicHistoryAddInf.getLivingChildren()).isEqualTo(DEFAULT_LIVING_CHILDREN);
        assertThat(testClinicHistoryAddInf.getLastMenarche()).isEqualTo(DEFAULT_LAST_MENARCHE);
        assertThat(testClinicHistoryAddInf.getLastDelivery()).isEqualTo(DEFAULT_LAST_DELIVERY);
        assertThat(testClinicHistoryAddInf.getLastCitology()).isEqualTo(DEFAULT_LAST_CITOLOGY);
        assertThat(testClinicHistoryAddInf.getBiopsy()).isEqualTo(DEFAULT_BIOPSY);
        assertThat(testClinicHistoryAddInf.getProtectionMethod()).isEqualTo(DEFAULT_PROTECTION_METHOD);
        assertThat(testClinicHistoryAddInf.getHormoneTerapy()).isEqualTo(DEFAULT_HORMONE_TERAPY);
        assertThat(testClinicHistoryAddInf.getColposcopy()).isEqualTo(DEFAULT_COLPOSCOPY);
        assertThat(testClinicHistoryAddInf.getMammografhy()).isEqualTo(DEFAULT_MAMMOGRAFHY);        
    }

    @Test
    @Transactional
    public void getAllClinicHistoryAddInfs() throws Exception {
        // Initialize the database
        clinicHistoryAddInfRepository.saveAndFlush(clinicHistoryAddInf);

        // Get all the clinicHistoryAddInfs
        restClinicHistoryAddInfMockMvc.perform(get("/api/clinicHistoryAddInfs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clinicHistoryAddInf.getId().intValue())))
                .andExpect(jsonPath("$.[*].menarcheAge").value(hasItem(DEFAULT_MENARCHE_AGE)))
                .andExpect(jsonPath("$.[*].menopauseAge").value(hasItem(DEFAULT_MENOPAUSE_AGE)))
                .andExpect(jsonPath("$.[*].cycles").value(hasItem(DEFAULT_CYCLES)))
                .andExpect(jsonPath("$.[*].sexuallyActive").value(hasItem(DEFAULT_SEXUALLY_ACTIVE.toString())))
                .andExpect(jsonPath("$.[*].feat").value(hasItem(DEFAULT_FEAT)))
                .andExpect(jsonPath("$.[*].deliveries").value(hasItem(DEFAULT_DELIVERIES)))
                .andExpect(jsonPath("$.[*].abortions").value(hasItem(DEFAULT_ABORTIONS)))
                .andExpect(jsonPath("$.[*].caesareans").value(hasItem(DEFAULT_CAESAREANS)))
                .andExpect(jsonPath("$.[*].livingChildren").value(hasItem(DEFAULT_LIVING_CHILDREN)))
                .andExpect(jsonPath("$.[*].lastMenarche").value(hasItem(DEFAULT_LAST_MENARCHE.toString())))
                .andExpect(jsonPath("$.[*].lastDelivery").value(hasItem(DEFAULT_LAST_DELIVERY.toString())))
                .andExpect(jsonPath("$.[*].lastCitology").value(hasItem(DEFAULT_LAST_CITOLOGY.toString())))
                .andExpect(jsonPath("$.[*].biopsy").value(hasItem(DEFAULT_BIOPSY.toString())))
                .andExpect(jsonPath("$.[*].protectionMethod").value(hasItem(DEFAULT_PROTECTION_METHOD.toString())))
                .andExpect(jsonPath("$.[*].hormoneTerapy").value(hasItem(DEFAULT_HORMONE_TERAPY.toString())))
                .andExpect(jsonPath("$.[*].colposcopy").value(hasItem(DEFAULT_COLPOSCOPY.toString())))
                .andExpect(jsonPath("$.[*].mammografhy").value(hasItem(DEFAULT_MAMMOGRAFHY.toString())));
    }

    @Test
    @Transactional
    public void getClinicHistoryAddInf() throws Exception {
        // Initialize the database
        clinicHistoryAddInfRepository.saveAndFlush(clinicHistoryAddInf);

        // Get the clinicHistoryAddInf
        restClinicHistoryAddInfMockMvc.perform(get("/api/clinicHistoryAddInfs/{id}", clinicHistoryAddInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(clinicHistoryAddInf.getId().intValue()))
            .andExpect(jsonPath("$.menarcheAge").value(DEFAULT_MENARCHE_AGE))
            .andExpect(jsonPath("$.menopauseAge").value(DEFAULT_MENOPAUSE_AGE))
            .andExpect(jsonPath("$.cycles").value(DEFAULT_CYCLES))
            .andExpect(jsonPath("$.sexuallyActive").value(DEFAULT_SEXUALLY_ACTIVE.toString()))
            .andExpect(jsonPath("$.feat").value(DEFAULT_FEAT))
            .andExpect(jsonPath("$.deliveries").value(DEFAULT_DELIVERIES))
            .andExpect(jsonPath("$.abortions").value(DEFAULT_ABORTIONS))
            .andExpect(jsonPath("$.caesareans").value(DEFAULT_CAESAREANS))
            .andExpect(jsonPath("$.livingChildren").value(DEFAULT_LIVING_CHILDREN))
            .andExpect(jsonPath("$.lastMenarche").value(DEFAULT_LAST_MENARCHE.toString()))
            .andExpect(jsonPath("$.lastDelivery").value(DEFAULT_LAST_DELIVERY.toString()))
            .andExpect(jsonPath("$.lastCitology").value(DEFAULT_LAST_CITOLOGY.toString()))
            .andExpect(jsonPath("$.biopsy").value(DEFAULT_BIOPSY.toString()))
            .andExpect(jsonPath("$.protectionMethod").value(DEFAULT_PROTECTION_METHOD.toString()))
            .andExpect(jsonPath("$.hormoneTerapy").value(DEFAULT_HORMONE_TERAPY.toString()))
            .andExpect(jsonPath("$.colposcopy").value(DEFAULT_COLPOSCOPY.toString()))
            .andExpect(jsonPath("$.mammografhy").value(DEFAULT_MAMMOGRAFHY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicHistoryAddInf() throws Exception {
        // Get the clinicHistoryAddInf
        restClinicHistoryAddInfMockMvc.perform(get("/api/clinicHistoryAddInfs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicHistoryAddInf() throws Exception {
        // Initialize the database
        clinicHistoryAddInfRepository.saveAndFlush(clinicHistoryAddInf);

		int databaseSizeBeforeUpdate = clinicHistoryAddInfRepository.findAll().size();

        // Update the clinicHistoryAddInf
        clinicHistoryAddInf.setMenarcheAge(UPDATED_MENARCHE_AGE);
        clinicHistoryAddInf.setMenopauseAge(UPDATED_MENOPAUSE_AGE);
        clinicHistoryAddInf.setCycles(UPDATED_CYCLES);
        clinicHistoryAddInf.setSexuallyActive(UPDATED_SEXUALLY_ACTIVE);
        clinicHistoryAddInf.setFeat(UPDATED_FEAT);
        clinicHistoryAddInf.setDeliveries(UPDATED_DELIVERIES);
        clinicHistoryAddInf.setAbortions(UPDATED_ABORTIONS);
        clinicHistoryAddInf.setCaesareans(UPDATED_CAESAREANS);
        clinicHistoryAddInf.setLivingChildren(UPDATED_LIVING_CHILDREN);
        clinicHistoryAddInf.setLastMenarche(UPDATED_LAST_MENARCHE);
        clinicHistoryAddInf.setLastDelivery(UPDATED_LAST_DELIVERY);
        clinicHistoryAddInf.setLastCitology(UPDATED_LAST_CITOLOGY);
        clinicHistoryAddInf.setBiopsy(UPDATED_BIOPSY);
        clinicHistoryAddInf.setProtectionMethod(UPDATED_PROTECTION_METHOD);
        clinicHistoryAddInf.setHormoneTerapy(UPDATED_HORMONE_TERAPY);
        clinicHistoryAddInf.setColposcopy(UPDATED_COLPOSCOPY);
        clinicHistoryAddInf.setMammografhy(UPDATED_MAMMOGRAFHY);
       
        

        restClinicHistoryAddInfMockMvc.perform(put("/api/clinicHistoryAddInfs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clinicHistoryAddInf)))
                .andExpect(status().isOk());

        // Validate the ClinicHistoryAddInf in the database
        List<ClinicHistoryAddInf> clinicHistoryAddInfs = clinicHistoryAddInfRepository.findAll();
        assertThat(clinicHistoryAddInfs).hasSize(databaseSizeBeforeUpdate);
        ClinicHistoryAddInf testClinicHistoryAddInf = clinicHistoryAddInfs.get(clinicHistoryAddInfs.size() - 1);
        assertThat(testClinicHistoryAddInf.getMenarcheAge()).isEqualTo(UPDATED_MENARCHE_AGE);
        assertThat(testClinicHistoryAddInf.getMenopauseAge()).isEqualTo(UPDATED_MENOPAUSE_AGE);
        assertThat(testClinicHistoryAddInf.getCycles()).isEqualTo(UPDATED_CYCLES);
        assertThat(testClinicHistoryAddInf.getSexuallyActive()).isEqualTo(UPDATED_SEXUALLY_ACTIVE);
        assertThat(testClinicHistoryAddInf.getFeat()).isEqualTo(UPDATED_FEAT);
        assertThat(testClinicHistoryAddInf.getDeliveries()).isEqualTo(UPDATED_DELIVERIES);
        assertThat(testClinicHistoryAddInf.getAbortions()).isEqualTo(UPDATED_ABORTIONS);
        assertThat(testClinicHistoryAddInf.getCaesareans()).isEqualTo(UPDATED_CAESAREANS);
        assertThat(testClinicHistoryAddInf.getLivingChildren()).isEqualTo(UPDATED_LIVING_CHILDREN);
        assertThat(testClinicHistoryAddInf.getLastMenarche()).isEqualTo(UPDATED_LAST_MENARCHE);
        assertThat(testClinicHistoryAddInf.getLastDelivery()).isEqualTo(UPDATED_LAST_DELIVERY);
        assertThat(testClinicHistoryAddInf.getLastCitology()).isEqualTo(UPDATED_LAST_CITOLOGY);
        assertThat(testClinicHistoryAddInf.getBiopsy()).isEqualTo(UPDATED_BIOPSY);
        assertThat(testClinicHistoryAddInf.getProtectionMethod()).isEqualTo(UPDATED_PROTECTION_METHOD);
        assertThat(testClinicHistoryAddInf.getHormoneTerapy()).isEqualTo(UPDATED_HORMONE_TERAPY);
        assertThat(testClinicHistoryAddInf.getColposcopy()).isEqualTo(UPDATED_COLPOSCOPY);
        assertThat(testClinicHistoryAddInf.getMammografhy()).isEqualTo(UPDATED_MAMMOGRAFHY);       
    }

    @Test
    @Transactional
    public void deleteClinicHistoryAddInf() throws Exception {
        // Initialize the database
        clinicHistoryAddInfRepository.saveAndFlush(clinicHistoryAddInf);

		int databaseSizeBeforeDelete = clinicHistoryAddInfRepository.findAll().size();

        // Get the clinicHistoryAddInf
        restClinicHistoryAddInfMockMvc.perform(delete("/api/clinicHistoryAddInfs/{id}", clinicHistoryAddInf.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicHistoryAddInf> clinicHistoryAddInfs = clinicHistoryAddInfRepository.findAll();
        assertThat(clinicHistoryAddInfs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
