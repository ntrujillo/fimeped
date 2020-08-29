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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.Person;
import org.uce.fimeped.repository.ClinicHistoryRepository;
import org.uce.fimeped.repository.PersonRepository;

/**
 * Test class for the ClinicHistoryResource REST controller.
 *
 * @see ClinicHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClinicHistoryResourceTest {

	private static final LocalDate DEFAULT_CREATE_DATE = new LocalDate(0L);
	private static final LocalDate UPDATED_CREATE_DATE = new LocalDate();

	@Inject
	private ClinicHistoryRepository clinicHistoryRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	private MockMvc restClinicHistoryMockMvc;

	private ClinicHistory clinicHistory;

	private Person person;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ClinicHistoryResource clinicHistoryResource = new ClinicHistoryResource();
		ReflectionTestUtils.setField(clinicHistoryResource, "clinicHistoryRepository", clinicHistoryRepository);
		this.restClinicHistoryMockMvc = MockMvcBuilders.standaloneSetup(clinicHistoryResource)
				.setMessageConverters(jacksonMessageConverter).build();
	}

	@Before
	public void initTest() {
		clinicHistory = new ClinicHistory();
		clinicHistory.setCreateDate(DEFAULT_CREATE_DATE);
	}

	@Test
	@Transactional
	public void createClinicHistory() throws Exception {
		int databaseSizeBeforeCreate = clinicHistoryRepository.findAll().size();

		// Create the ClinicHistory

		restClinicHistoryMockMvc.perform(post("/api/clinicHistorys").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(clinicHistory))).andExpect(status().isCreated());

		// Validate the ClinicHistory in the database
		List<ClinicHistory> clinicHistorys = clinicHistoryRepository.findAll();
		assertThat(clinicHistorys).hasSize(databaseSizeBeforeCreate + 1);
		ClinicHistory testClinicHistory = clinicHistorys.get(clinicHistorys.size() - 1);
		assertThat(testClinicHistory.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
	}

	@Test
	@Transactional
	public void getAllClinicHistorys() throws Exception {
		// Initialize the database
		clinicHistoryRepository.saveAndFlush(clinicHistory);

		// Get all the clinicHistorys
		restClinicHistoryMockMvc.perform(get("/api/clinicHistorys").param("page", "1").param("per_page", "5"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.[*].id").value(hasItem(1)));
				

	}

	@Test
	@Transactional
	public void getClinicHistoryByFilter() throws Exception {
		// Initialize the database
		person = new Person();
		person.setFirstName("Pedro");
		person.setSecondName("Oliverto");
		person.setPaternalSurname("Perez");
		person.setMaritalStatus("Martinez");
		person.setCedula("1719004218");
		clinicHistory.setPerson(person);
		personRepository.saveAndFlush(person);
		clinicHistory.setPerson(person);
		clinicHistoryRepository.saveAndFlush(clinicHistory);

		// Get all the clinicHistorys
		restClinicHistoryMockMvc
				.perform(get("/api/clinicHistorys").param("page", "1").param("per_page", "5")
						.param("firstName", "pedro").param("paternalSurname", "perez").param("cedula", "1719004218")).andDo(print())
				.andExpect(jsonPath("$.[*].person.firstName").value(hasItem("Pedro")))
				.andExpect(jsonPath("$.[*].person.paternalSurname").value(hasItem("Perez")))
				.andExpect(jsonPath("$.[*].person.cedula").value(hasItem("1719004218")));
				

		personRepository.delete(person);
		clinicHistory.setPerson(null);
	}

	@Test
	@Transactional
	public void getClinicHistory() throws Exception {
		// Initialize the database
		clinicHistoryRepository.saveAndFlush(clinicHistory);

		// Get the clinicHistory
		restClinicHistoryMockMvc.perform(get("/api/clinicHistorys/{id}", clinicHistory.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(clinicHistory.getId().intValue()))
				.andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingClinicHistory() throws Exception {
		// Get the clinicHistory
		restClinicHistoryMockMvc.perform(get("/api/clinicHistorys/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateClinicHistory() throws Exception {
		// Initialize the database
		clinicHistoryRepository.saveAndFlush(clinicHistory);

		int databaseSizeBeforeUpdate = clinicHistoryRepository.findAll().size();

		// Update the clinicHistory
		clinicHistory.setCreateDate(UPDATED_CREATE_DATE);

		restClinicHistoryMockMvc.perform(put("/api/clinicHistorys").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(clinicHistory))).andExpect(status().isOk());

		// Validate the ClinicHistory in the database
		List<ClinicHistory> clinicHistorys = clinicHistoryRepository.findAll();
		assertThat(clinicHistorys).hasSize(databaseSizeBeforeUpdate);
		ClinicHistory testClinicHistory = clinicHistorys.get(clinicHistorys.size() - 1);
		assertThat(testClinicHistory.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
	}

	@Test
	@Transactional
	public void deleteClinicHistory() throws Exception {
		// Initialize the database
		clinicHistoryRepository.saveAndFlush(clinicHistory);

		int databaseSizeBeforeDelete = clinicHistoryRepository.findAll().size();

		// Get the clinicHistory
		restClinicHistoryMockMvc.perform(
				delete("/api/clinicHistorys/{id}", clinicHistory.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<ClinicHistory> clinicHistorys = clinicHistoryRepository.findAll();
		assertThat(clinicHistorys).hasSize(databaseSizeBeforeDelete - 1);
	}
}
