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
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.VitalSign;
import org.uce.fimeped.repository.ClinicHistoryRepository;
import org.uce.fimeped.repository.VitalSignRepository;


/**
 * Test class for the VitalSignResource REST controller.
 *
 * @see VitalSignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VitalSignResourceTest {

    private static final String DEFAULT_BLOOD_PRESSURE = "SAMPLE_TEXT";
    private static final String UPDATED_BLOOD_PRESSURE = "UPDATED_TEXT";

    private static final Integer DEFAULT_HEART_RATE = 1;
    private static final Integer UPDATED_HEART_RATE = 2;

    private static final Integer DEFAULT_BREATHING_FRECUENCY = 1;
    private static final Integer UPDATED_BREATHING_FRECUENCY = 2;

    private static final Double DEFAULT_ORAL_TEMPERATURE = 1D;
    private static final Double UPDATED_ORAL_TEMPERATURE = 2D;

    private static final Double DEFAULT_AXILLARY_TEMPERATURE = 1D;
    private static final Double UPDATED_AXILLARY_TEMPERATURE = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final Double DEFAULT_HEAD_CIRCUMFERENCE = 1D;
    private static final Double UPDATED_HEAD_CIRCUMFERENCE = 2D;

    private static final Double DEFAULT_BODY_MASS = 1D;
    private static final Double UPDATED_BODY_MASS = 2D;

    @Inject
    private VitalSignRepository vitalSignRepository;
    
    @Inject
    private ClinicHistoryRepository clinicHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restVitalSignMockMvc;

    private VitalSign vitalSign;
    
    private ClinicHistory clinicHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VitalSignResource vitalSignResource = new VitalSignResource();        
        ReflectionTestUtils.setField(vitalSignResource, "vitalSignRepository", vitalSignRepository);
        this.restVitalSignMockMvc = MockMvcBuilders.standaloneSetup(vitalSignResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vitalSign = new VitalSign();
        vitalSign.setBloodPressure(DEFAULT_BLOOD_PRESSURE);
        vitalSign.setHeartRate(DEFAULT_HEART_RATE);
        vitalSign.setBreathingFrecuency(DEFAULT_BREATHING_FRECUENCY);
        vitalSign.setOralTemperature(DEFAULT_ORAL_TEMPERATURE);
        vitalSign.setAxillaryTemperature(DEFAULT_AXILLARY_TEMPERATURE);
        vitalSign.setWeight(DEFAULT_WEIGHT);
        vitalSign.setSize(DEFAULT_SIZE);
        vitalSign.setHeadCircumference(DEFAULT_HEAD_CIRCUMFERENCE);
        vitalSign.setBodyMass(DEFAULT_BODY_MASS);        
        clinicHistory = clinicHistoryRepository.save(new ClinicHistory());
       
    }

    @Test
    @Transactional
    public void createVitalSign() throws Exception {
        int databaseSizeBeforeCreate = vitalSignRepository.findAll().size();

        // Create the VitalSign

        restVitalSignMockMvc.perform(post("/api/clinicHistorys/"+clinicHistory.getId()+"/vitalSigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vitalSign)))
                .andExpect(status().isCreated());

        // Validate the VitalSign in the database
        List<VitalSign> vitalSigns = vitalSignRepository.findAll();
        assertThat(vitalSigns).hasSize(databaseSizeBeforeCreate + 1);
        VitalSign testVitalSign = vitalSigns.get(vitalSigns.size() - 1);
        assertThat(testVitalSign.getBloodPressure()).isEqualTo(DEFAULT_BLOOD_PRESSURE);
        assertThat(testVitalSign.getHeartRate()).isEqualTo(DEFAULT_HEART_RATE);
        assertThat(testVitalSign.getBreathingFrecuency()).isEqualTo(DEFAULT_BREATHING_FRECUENCY);
        assertThat(testVitalSign.getOralTemperature()).isEqualTo(DEFAULT_ORAL_TEMPERATURE);
        assertThat(testVitalSign.getAxillaryTemperature()).isEqualTo(DEFAULT_AXILLARY_TEMPERATURE);
        assertThat(testVitalSign.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testVitalSign.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testVitalSign.getHeadCircumference()).isEqualTo(DEFAULT_HEAD_CIRCUMFERENCE);
        assertThat(testVitalSign.getBodyMass()).isEqualTo(DEFAULT_BODY_MASS);
    }

    @Test
    @Transactional
    public void getAllVitalSigns() throws Exception {
        // Initialize the database
        vitalSignRepository.saveAndFlush(vitalSign);

        // Get all the vitalSigns
        restVitalSignMockMvc.perform(get("/api/clinicHistorys/"+clinicHistory.getId()+"/vitalSigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vitalSign.getId().intValue())))
                .andExpect(jsonPath("$.[*].bloodPressure").value(hasItem(DEFAULT_BLOOD_PRESSURE.toString())))
                .andExpect(jsonPath("$.[*].heartRate").value(hasItem(DEFAULT_HEART_RATE)))
                .andExpect(jsonPath("$.[*].breathingFrecuency").value(hasItem(DEFAULT_BREATHING_FRECUENCY)))
                .andExpect(jsonPath("$.[*].oralTemperature").value(hasItem(DEFAULT_ORAL_TEMPERATURE.doubleValue())))
                .andExpect(jsonPath("$.[*].axillaryTemperature").value(hasItem(DEFAULT_AXILLARY_TEMPERATURE.doubleValue())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
                .andExpect(jsonPath("$.[*].headCircumference").value(hasItem(DEFAULT_HEAD_CIRCUMFERENCE.doubleValue())))
                .andExpect(jsonPath("$.[*].bodyMass").value(hasItem(DEFAULT_BODY_MASS.doubleValue())));
    }

    @Test
    @Transactional
    public void getVitalSign() throws Exception {
        // Initialize the database
        vitalSignRepository.saveAndFlush(vitalSign);

        // Get the vitalSign
        restVitalSignMockMvc.perform(get("/api/clinicHistorys/"+clinicHistory.getId()+"/vitalSigns/{id}", vitalSign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vitalSign.getId().intValue()))
            .andExpect(jsonPath("$.bloodPressure").value(DEFAULT_BLOOD_PRESSURE.toString()))
            .andExpect(jsonPath("$.heartRate").value(DEFAULT_HEART_RATE))
            .andExpect(jsonPath("$.breathingFrecuency").value(DEFAULT_BREATHING_FRECUENCY))
            .andExpect(jsonPath("$.oralTemperature").value(DEFAULT_ORAL_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.axillaryTemperature").value(DEFAULT_AXILLARY_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.headCircumference").value(DEFAULT_HEAD_CIRCUMFERENCE.doubleValue()))
            .andExpect(jsonPath("$.bodyMass").value(DEFAULT_BODY_MASS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVitalSign() throws Exception {
        // Get the vitalSign
        restVitalSignMockMvc.perform(get("/api/clinicHistorys/"+clinicHistory.getId()+"/vitalSigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVitalSign() throws Exception {
        // Initialize the database
        vitalSignRepository.saveAndFlush(vitalSign);

		int databaseSizeBeforeUpdate = vitalSignRepository.findAll().size();

        // Update the vitalSign
        vitalSign.setBloodPressure(UPDATED_BLOOD_PRESSURE);
        vitalSign.setHeartRate(UPDATED_HEART_RATE);
        vitalSign.setBreathingFrecuency(UPDATED_BREATHING_FRECUENCY);
        vitalSign.setOralTemperature(UPDATED_ORAL_TEMPERATURE);
        vitalSign.setAxillaryTemperature(UPDATED_AXILLARY_TEMPERATURE);
        vitalSign.setWeight(UPDATED_WEIGHT);
        vitalSign.setSize(UPDATED_SIZE);
        vitalSign.setHeadCircumference(UPDATED_HEAD_CIRCUMFERENCE);
        vitalSign.setBodyMass(UPDATED_BODY_MASS);
        

        restVitalSignMockMvc.perform(put("/api/vitalSigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vitalSign)))
                .andExpect(status().isOk());

        // Validate the VitalSign in the database
        List<VitalSign> vitalSigns = vitalSignRepository.findAll();
        assertThat(vitalSigns).hasSize(databaseSizeBeforeUpdate);
        VitalSign testVitalSign = vitalSigns.get(vitalSigns.size() - 1);
        assertThat(testVitalSign.getBloodPressure()).isEqualTo(UPDATED_BLOOD_PRESSURE);
        assertThat(testVitalSign.getHeartRate()).isEqualTo(UPDATED_HEART_RATE);
        assertThat(testVitalSign.getBreathingFrecuency()).isEqualTo(UPDATED_BREATHING_FRECUENCY);
        assertThat(testVitalSign.getOralTemperature()).isEqualTo(UPDATED_ORAL_TEMPERATURE);
        assertThat(testVitalSign.getAxillaryTemperature()).isEqualTo(UPDATED_AXILLARY_TEMPERATURE);
        assertThat(testVitalSign.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testVitalSign.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testVitalSign.getHeadCircumference()).isEqualTo(UPDATED_HEAD_CIRCUMFERENCE);
        assertThat(testVitalSign.getBodyMass()).isEqualTo(UPDATED_BODY_MASS);
    }

    @Test
    @Transactional
    public void deleteVitalSign() throws Exception {
        // Initialize the database
        vitalSignRepository.saveAndFlush(vitalSign);

		int databaseSizeBeforeDelete = vitalSignRepository.findAll().size();

        // Get the vitalSign
        restVitalSignMockMvc.perform(delete("/api/clinicHistorys/"+clinicHistory.getId()+"/vitalSigns/{id}", vitalSign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VitalSign> vitalSigns = vitalSignRepository.findAll();
        assertThat(vitalSigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
