package org.uce.fimeped.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItem;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.uce.fimeped.Application;
import org.uce.fimeped.repository.CatalogRepository;
import org.uce.fimeped.repository.ICD10Repository;

/**
 * Test class for the CatalogResource REST controller.
 *
 * @see CatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CatalogResourceTest {

	@Inject
	private CatalogRepository catalogRepository;
	
	@Inject
	private ICD10Repository icd10Repository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	private MockMvc restCatalogMockMvc;

	private static final String tabla = "cl_003_2";
	
	private static final int lengthTabla = 24;

	@Before
	public void initTest() {
		CatalogResource catalogResource = new CatalogResource();		
		ReflectionTestUtils.setField(catalogResource, "catalogRepository", catalogRepository);
		ReflectionTestUtils.setField(catalogResource, "icd10Repository", icd10Repository);
		this.restCatalogMockMvc = MockMvcBuilders.standaloneSetup(catalogResource)
				.setMessageConverters(jacksonMessageConverter).build();

	}

	@Test
	@Transactional
	public void testGetCatalog() throws Exception {
		
		restCatalogMockMvc.perform(get("/api/tablas/{tabla}/catalogs", tabla))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[*]").isArray()).andExpect(jsonPath("$.[*]",Matchers.hasSize(lengthTabla))).andDo(print());
	}
	
	@Test
	@Transactional
	public void testIcd10() throws Exception {
		
		restCatalogMockMvc.perform(get("/api/icd10").param("page", "1").param("per_page", "5").param("code", "").param("description","colera"))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[*]").isArray()).andExpect(jsonPath("$.[*].description").value(hasItem("COLERA")))
				.andExpect(jsonPath("$.[*]",Matchers.hasSize(5))).andDo(print());
	}

}
