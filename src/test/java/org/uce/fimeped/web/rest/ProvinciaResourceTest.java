package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.Provincia;
import org.uce.fimeped.repository.ProvinciaRepository;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProvinciaResource REST controller.
 *
 * @see ProvinciaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProvinciaResourceTest {

	private static final String DEFAULT_ID = "01-554";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private ProvinciaRepository provinciaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restProvinciaMockMvc;

    private Provincia provincia;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProvinciaResource provinciaResource = new ProvinciaResource();
        ReflectionTestUtils.setField(provinciaResource, "provinciaRepository", provinciaRepository);
        this.restProvinciaMockMvc = MockMvcBuilders.standaloneSetup(provinciaResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        provincia = new Provincia();
        provincia.setId(DEFAULT_ID);
        provincia.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProvincia() throws Exception {
        int databaseSizeBeforeCreate = provinciaRepository.findAll().size();

        // Create the Provincia

        System.out.println("trama Json: "+TestUtil.convertObjectToJsonBytes(provincia).toString());
        restProvinciaMockMvc.perform(post("/api/provincias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provincia))).andDo(print())
                .andExpect(status().isCreated());

        // Validate the Provincia in the database
        List<Provincia> provincias = provinciaRepository.findAll();
        assertThat(provincias).hasSize(databaseSizeBeforeCreate + 1);
        Provincia testProvincia = provincias.get(provincias.size() - 1);
        assertThat(testProvincia.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinciaRepository.findAll().size();
        // set the field null
        provincia.setId(null);

        // Create the Provincia, which fails.

        restProvinciaMockMvc.perform(post("/api/provincias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provincia)))
                .andExpect(status().isBadRequest());

        List<Provincia> provincias = provinciaRepository.findAll();
        assertThat(provincias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProvincias() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provincias
        restProvinciaMockMvc.perform(get("/api/provincias"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
               // .andExpect(jsonPath("$.[*].id").value(hasItem(provincia.getId())))
                //.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get the provincia
        restProvinciaMockMvc.perform(get("/api/provincias/{id}", provincia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(provincia.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProvincia() throws Exception {
        // Get the provincia
        restProvinciaMockMvc.perform(get("/api/provincias/{id}", "NOEXISTE"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

		int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();

        // Update the provincia
        provincia.setName(UPDATED_NAME);
        

        restProvinciaMockMvc.perform(put("/api/provincias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provincia)))
                .andExpect(status().isOk());

        // Validate the Provincia in the database
        List<Provincia> provincias = provinciaRepository.findAll();
        assertThat(provincias).hasSize(databaseSizeBeforeUpdate);
        Provincia testProvincia = provincias.get(provincias.size() - 1);
        assertThat(testProvincia.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

		int databaseSizeBeforeDelete = provinciaRepository.findAll().size();

        // Get the provincia
        restProvinciaMockMvc.perform(delete("/api/provincias/{id}", provincia.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Provincia> provincias = provinciaRepository.findAll();
        assertThat(provincias).hasSize(databaseSizeBeforeDelete - 1);
    }
}
