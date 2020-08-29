package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.Person;
import org.uce.fimeped.repository.ClinicHistoryRepository;
import org.uce.fimeped.repository.EpisodeRepository;

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

import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EpisodeResource REST controller.
 *
 * @see EpisodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EpisodeResourceTest {

	private static final LocalDate DEFAULT_DATE = new LocalDate();
	private static final LocalDate UPDATED_DATE = new LocalDate();
	private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
	private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

	@Inject
	private EpisodeRepository episodeRepository;
	
	@Inject
	private ClinicHistoryRepository clinicRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	private MockMvc restEpisodeMockMvc;

	private Episode episode;

	private ClinicHistory clinicHistory;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		EpisodeResource episodeResource = new EpisodeResource();
		ReflectionTestUtils.setField(episodeResource, "episodeRepository", episodeRepository);
		this.restEpisodeMockMvc = MockMvcBuilders.standaloneSetup(episodeResource)
				.setMessageConverters(jacksonMessageConverter).build();
	}

	@Before
	public void initTest() {				
		clinicHistory = clinicRepository.saveAndFlush(new ClinicHistory());		
		episode = new Episode();
		episode.setClinicHistory(clinicHistory);
		episode.setDate(DEFAULT_DATE);
		episode.setDescription(DEFAULT_DESCRIPTION);
	}

	@Test
	@Transactional
	public void createEpisode() throws Exception {
		int databaseSizeBeforeCreate = episodeRepository.findAll().size();

		// Create the Episode

		restEpisodeMockMvc
				.perform(post("/api/episodes").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(episode)))
				.andExpect(status().isCreated()).andDo(print());

		// Validate the Episode in the database
		List<Episode> episodes = episodeRepository.findAll();
		assertThat(episodes).hasSize(databaseSizeBeforeCreate + 1);
		Episode testEpisode = episodes.get(episodes.size() - 1);
		assertThat(testEpisode.getDate()).isEqualTo(DEFAULT_DATE);
		assertThat(testEpisode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
	}

	@Test
	@Transactional
	public void getAllEpisodes() throws Exception {
		// Initialize the database
		episodeRepository.saveAndFlush(episode);
		int count = episodeRepository.findAll().size();

		// Get all the episodes
		restEpisodeMockMvc.perform(get("/api/episodes").param("page", "1").param("per_page", "5"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print());
	}

	@Test
	@Transactional
	public void getEpisodeByFilterStartDate() throws Exception {
		episodeRepository.saveAndFlush(episode);
		restEpisodeMockMvc
				.perform(get("/api/episodes").param("page", "1").param("per_page", "20").param("history_id", clinicHistory.getId().toString()).param("date_start",
						DEFAULT_DATE.toString()))
				.andDo(print())
				.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
				
				
	}

	@Test
	@Transactional
	public void getEpisodeByFilterEndDate() throws Exception {
		episodeRepository.saveAndFlush(episode);
		restEpisodeMockMvc
				.perform(get("/api/episodes").param("page", "1").param("per_page", "20").param("date_end",
						DEFAULT_DATE.toString()))
				.andDo(print())
				.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
	}

	@Test
	@Transactional
	public void getEpisodeByFilterBetween() throws Exception {
		episodeRepository.saveAndFlush(episode);
		restEpisodeMockMvc
				.perform(get("/api/episodes").param("page", "1").param("per_page", "20")
						.param("date_start", DEFAULT_DATE.toString()).param("date_end", DEFAULT_DATE.toString()))
				.andDo(print())
				.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
	}

	@Test
	@Transactional
	public void getEpisode() throws Exception {
		// Initialize the database
		episodeRepository.saveAndFlush(episode);

		// Get the episode
		restEpisodeMockMvc.perform(get("/api/episodes/{id}", episode.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(episode.getId().intValue()))
				.andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingEpisode() throws Exception {
		// Get the episode
		restEpisodeMockMvc.perform(get("/api/episodes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateEpisode() throws Exception {
		// Initialize the database
		episodeRepository.saveAndFlush(episode);

		int databaseSizeBeforeUpdate = episodeRepository.findAll().size();

		// Update the episode
		episode.setDate(UPDATED_DATE);
		episode.setDescription(UPDATED_DESCRIPTION);

		restEpisodeMockMvc.perform(put("/api/episodes").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(episode))).andExpect(status().isOk());

		// Validate the Episode in the database
		List<Episode> episodes = episodeRepository.findAll();
		assertThat(episodes).hasSize(databaseSizeBeforeUpdate);
		Episode testEpisode = episodes.get(episodes.size() - 1);
		assertThat(testEpisode.getDate()).isEqualTo(UPDATED_DATE);
		assertThat(testEpisode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
	}

	@Test
	@Transactional
	public void deleteEpisode() throws Exception {
		// Initialize the database
		episodeRepository.saveAndFlush(episode);

		int databaseSizeBeforeDelete = episodeRepository.findAll().size();

		// Get the episode
		restEpisodeMockMvc.perform(delete("/api/episodes/{id}", episode.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Episode> episodes = episodeRepository.findAll();
		assertThat(episodes).hasSize(databaseSizeBeforeDelete - 1);
	}
}
