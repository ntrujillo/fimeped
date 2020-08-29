package org.uce.fimeped.web.rest;

import org.uce.fimeped.Application;
import org.uce.fimeped.domain.Person;
import org.uce.fimeped.repository.PersonRepository;

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
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonResourceTest {

    private static final String DEFAULT_SYSTEM_INSTITUTION = "SAMPLE_TEXT";
    private static final String UPDATED_SYSTEM_INSTITUTION = "UPDATED_TEXT";
    private static final String DEFAULT_OPERATING_UNIT = "SAMPLE_TEXT";
    private static final String UPDATED_OPERATING_UNIT = "UPDATED_TEXT";

    private static final Integer DEFAULT_OPERATING_UNIT_CODE = 1;
    private static final Integer UPDATED_OPERATING_UNIT_CODE = 2;
    private static final String DEFAULT_PERSON_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_PERSON_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_PERSON_FACULTY = "SAMPLE_TEXT";
    private static final String UPDATED_PERSON_FACULTY = "UPDATED_TEXT";
    private static final String DEFAULT_PERSON_CAREER = "SAMPLE_TEXT";
    private static final String UPDATED_PERSON_CAREER = "UPDATED_TEXT";

    private static final Integer DEFAULT_PERSON_SEMESTER = 1;
    private static final Integer UPDATED_PERSON_SEMESTER = 2;
    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SECOND_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_SECOND_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_PATERNAL_SURNAME = "SAMPLE_TEXT";
    private static final String UPDATED_PATERNAL_SURNAME = "UPDATED_TEXT";
    private static final String DEFAULT_MATERNAL_SURNAME = "SAMPLE_TEXT";
    private static final String UPDATED_MATERNAL_SURNAME = "UPDATED_TEXT";
    private static final String DEFAULT_CEDULA = "SAMPLE_TEXT";
    private static final String UPDATED_CEDULA = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_BIRTH_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = new LocalDate();
    private static final String DEFAULT_BIRTH_PLACE = "SAMPLE_TEXT";
    private static final String UPDATED_BIRTH_PLACE = "UPDATED_TEXT";
    private static final String DEFAULT_NATIONALITY = "SAMPLE_TEXT";
    private static final String UPDATED_NATIONALITY = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final String DEFAULT_GENDER = "SAMPLE_TEXT";
    private static final String UPDATED_GENDER = "UPDATED_TEXT";
    private static final String DEFAULT_MARITAL_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_MARITAL_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_APPROVED_INSTRUCTION_YEAR = "SAMPLE_TEXT";
    private static final String UPDATED_APPROVED_INSTRUCTION_YEAR = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_ADMISSION_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_ADMISSION_DATE = new LocalDate();
    private static final String DEFAULT_OCCUPATION = "SAMPLE_TEXT";
    private static final String UPDATED_OCCUPATION = "UPDATED_TEXT";
    private static final String DEFAULT_COMPANY_WORK = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_WORK = "UPDATED_TEXT";
    private static final String DEFAULT_INSURANCE_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_INSURANCE_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_REFERRED = "SAMPLE_TEXT";
    private static final String UPDATED_REFERRED = "UPDATED_TEXT";
    private static final String DEFAULT_KINSHIP_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_KINSHIP_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_KINSHIP = "SAMPLE_TEXT";
    private static final String UPDATED_KINSHIP = "UPDATED_TEXT";
    private static final String DEFAULT_KINSHIP_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_KINSHIP_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_KINSHIP_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_KINSHIP_PHONE = "UPDATED_TEXT";
    private static final String DEFAULT_NEIGHBORHOOD = "SAMPLE_TEXT";
    private static final String UPDATED_NEIGHBORHOOD = "UPDATED_TEXT";
    private static final String DEFAULT_PARROQUIA = "SAMPLE_TEXT";
    private static final String UPDATED_PARROQUIA = "UPDATED_TEXT";
    private static final String DEFAULT_CANTON = "SAMPLE_TEXT";
    private static final String UPDATED_CANTON = "UPDATED_TEXT";
    private static final String DEFAULT_PROVINCIA = "SAMPLE_TEXT";
    private static final String UPDATED_PROVINCIA = "UPDATED_TEXT";
    private static final String DEFAULT_ZONE = "SAMPLE_TEXT";
    private static final String UPDATED_ZONE = "UPDATED_TEXT";
    private static final String DEFAULT_CULTURAL_GROUP = "SAMPLE_TEXT";
    private static final String UPDATED_CULTURAL_GROUP = "UPDATED_TEXT";

    @Inject
    private PersonRepository personRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personRepository", personRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        person = new Person();
        person.setSystemInstitution(DEFAULT_SYSTEM_INSTITUTION);
        person.setOperatingUnit(DEFAULT_OPERATING_UNIT);
        person.setOperatingUnitCode(DEFAULT_OPERATING_UNIT_CODE);
        person.setPersonType(DEFAULT_PERSON_TYPE);
        person.setPersonFaculty(DEFAULT_PERSON_FACULTY);
        person.setPersonCareer(DEFAULT_PERSON_CAREER);
        person.setPersonSemester(DEFAULT_PERSON_SEMESTER);
        person.setFirstName(DEFAULT_FIRST_NAME);
        person.setSecondName(DEFAULT_SECOND_NAME);
        person.setPaternalSurname(DEFAULT_PATERNAL_SURNAME);
        person.setMaternalSurname(DEFAULT_MATERNAL_SURNAME);
        person.setCedula(DEFAULT_CEDULA);
        person.setAddress(DEFAULT_ADDRESS);
        person.setPhone(DEFAULT_PHONE);
        person.setBirthDate(DEFAULT_BIRTH_DATE);
        person.setBirthPlace(DEFAULT_BIRTH_PLACE);
        person.setNationality(DEFAULT_NATIONALITY);
        person.setAge(DEFAULT_AGE);
        person.setGender(DEFAULT_GENDER);
        person.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        person.setApprovedInstructionYear(DEFAULT_APPROVED_INSTRUCTION_YEAR);
        person.setAdmissionDate(DEFAULT_ADMISSION_DATE);
        person.setOccupation(DEFAULT_OCCUPATION);
        person.setCompanyWork(DEFAULT_COMPANY_WORK);
        person.setInsuranceType(DEFAULT_INSURANCE_TYPE);
        person.setReferred(DEFAULT_REFERRED);
        person.setKinshipName(DEFAULT_KINSHIP_NAME);
        person.setKinship(DEFAULT_KINSHIP);
        person.setKinshipAddress(DEFAULT_KINSHIP_ADDRESS);
        person.setKinshipPhone(DEFAULT_KINSHIP_PHONE);
        person.setNeighborhood(DEFAULT_NEIGHBORHOOD);
        person.setParroquia(DEFAULT_PARROQUIA);
        person.setCanton(DEFAULT_CANTON);
        person.setProvincia(DEFAULT_PROVINCIA);
        person.setZone(DEFAULT_ZONE);
        person.setCulturalGroup(DEFAULT_CULTURAL_GROUP);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person

        restPersonMockMvc.perform(post("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = persons.get(persons.size() - 1);
        assertThat(testPerson.getSystemInstitution()).isEqualTo(DEFAULT_SYSTEM_INSTITUTION);
        assertThat(testPerson.getOperatingUnit()).isEqualTo(DEFAULT_OPERATING_UNIT);
        assertThat(testPerson.getOperatingUnitCode()).isEqualTo(DEFAULT_OPERATING_UNIT_CODE);
        assertThat(testPerson.getPersonType()).isEqualTo(DEFAULT_PERSON_TYPE);
        assertThat(testPerson.getPersonFaculty()).isEqualTo(DEFAULT_PERSON_FACULTY);
        assertThat(testPerson.getPersonCareer()).isEqualTo(DEFAULT_PERSON_CAREER);
        assertThat(testPerson.getPersonSemester()).isEqualTo(DEFAULT_PERSON_SEMESTER);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testPerson.getPaternalSurname()).isEqualTo(DEFAULT_PATERNAL_SURNAME);
        assertThat(testPerson.getMaternalSurname()).isEqualTo(DEFAULT_MATERNAL_SURNAME);
        assertThat(testPerson.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testPerson.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPerson.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPerson.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPerson.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPerson.getApprovedInstructionYear()).isEqualTo(DEFAULT_APPROVED_INSTRUCTION_YEAR);
        assertThat(testPerson.getAdmissionDate()).isEqualTo(DEFAULT_ADMISSION_DATE);
        assertThat(testPerson.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPerson.getCompanyWork()).isEqualTo(DEFAULT_COMPANY_WORK);
        assertThat(testPerson.getInsuranceType()).isEqualTo(DEFAULT_INSURANCE_TYPE);
        assertThat(testPerson.getReferred()).isEqualTo(DEFAULT_REFERRED);
        assertThat(testPerson.getKinshipName()).isEqualTo(DEFAULT_KINSHIP_NAME);
        assertThat(testPerson.getKinship()).isEqualTo(DEFAULT_KINSHIP);
        assertThat(testPerson.getKinshipAddress()).isEqualTo(DEFAULT_KINSHIP_ADDRESS);
        assertThat(testPerson.getKinshipPhone()).isEqualTo(DEFAULT_KINSHIP_PHONE);
        assertThat(testPerson.getNeighborhood()).isEqualTo(DEFAULT_NEIGHBORHOOD);
        assertThat(testPerson.getParroquia()).isEqualTo(DEFAULT_PARROQUIA);
        assertThat(testPerson.getCanton()).isEqualTo(DEFAULT_CANTON);
        assertThat(testPerson.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testPerson.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testPerson.getCulturalGroup()).isEqualTo(DEFAULT_CULTURAL_GROUP);
    }

    @Test
    @Transactional
    public void getAllPersons() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the persons
        restPersonMockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
                .andExpect(jsonPath("$.[*].systemInstitution").value(hasItem(DEFAULT_SYSTEM_INSTITUTION.toString())))
                .andExpect(jsonPath("$.[*].operatingUnit").value(hasItem(DEFAULT_OPERATING_UNIT.toString())))
                .andExpect(jsonPath("$.[*].operatingUnitCode").value(hasItem(DEFAULT_OPERATING_UNIT_CODE)))
                .andExpect(jsonPath("$.[*].personType").value(hasItem(DEFAULT_PERSON_TYPE.toString())))
                .andExpect(jsonPath("$.[*].personFaculty").value(hasItem(DEFAULT_PERSON_FACULTY.toString())))
                .andExpect(jsonPath("$.[*].personCareer").value(hasItem(DEFAULT_PERSON_CAREER.toString())))
                .andExpect(jsonPath("$.[*].personSemester").value(hasItem(DEFAULT_PERSON_SEMESTER)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME.toString())))
                .andExpect(jsonPath("$.[*].paternalSurname").value(hasItem(DEFAULT_PATERNAL_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].maternalSurname").value(hasItem(DEFAULT_MATERNAL_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].approvedInstructionYear").value(hasItem(DEFAULT_APPROVED_INSTRUCTION_YEAR.toString())))
                .andExpect(jsonPath("$.[*].admissionDate").value(hasItem(DEFAULT_ADMISSION_DATE.toString())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].companyWork").value(hasItem(DEFAULT_COMPANY_WORK.toString())))
                .andExpect(jsonPath("$.[*].insuranceType").value(hasItem(DEFAULT_INSURANCE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].referred").value(hasItem(DEFAULT_REFERRED.toString())))
                .andExpect(jsonPath("$.[*].kinshipName").value(hasItem(DEFAULT_KINSHIP_NAME.toString())))
                .andExpect(jsonPath("$.[*].kinship").value(hasItem(DEFAULT_KINSHIP.toString())))
                .andExpect(jsonPath("$.[*].kinshipAddress").value(hasItem(DEFAULT_KINSHIP_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].kinshipPhone").value(hasItem(DEFAULT_KINSHIP_PHONE.toString())))
                .andExpect(jsonPath("$.[*].neighborhood").value(hasItem(DEFAULT_NEIGHBORHOOD.toString())))
                .andExpect(jsonPath("$.[*].parroquia").value(hasItem(DEFAULT_PARROQUIA.toString())))
                .andExpect(jsonPath("$.[*].canton").value(hasItem(DEFAULT_CANTON.toString())))
                .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
                .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
                .andExpect(jsonPath("$.[*].culturalGroup").value(hasItem(DEFAULT_CULTURAL_GROUP.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.systemInstitution").value(DEFAULT_SYSTEM_INSTITUTION.toString()))
            .andExpect(jsonPath("$.operatingUnit").value(DEFAULT_OPERATING_UNIT.toString()))
            .andExpect(jsonPath("$.operatingUnitCode").value(DEFAULT_OPERATING_UNIT_CODE))
            .andExpect(jsonPath("$.personType").value(DEFAULT_PERSON_TYPE.toString()))
            .andExpect(jsonPath("$.personFaculty").value(DEFAULT_PERSON_FACULTY.toString()))
            .andExpect(jsonPath("$.personCareer").value(DEFAULT_PERSON_CAREER.toString()))
            .andExpect(jsonPath("$.personSemester").value(DEFAULT_PERSON_SEMESTER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME.toString()))
            .andExpect(jsonPath("$.paternalSurname").value(DEFAULT_PATERNAL_SURNAME.toString()))
            .andExpect(jsonPath("$.maternalSurname").value(DEFAULT_MATERNAL_SURNAME.toString()))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.approvedInstructionYear").value(DEFAULT_APPROVED_INSTRUCTION_YEAR.toString()))
            .andExpect(jsonPath("$.admissionDate").value(DEFAULT_ADMISSION_DATE.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.companyWork").value(DEFAULT_COMPANY_WORK.toString()))
            .andExpect(jsonPath("$.insuranceType").value(DEFAULT_INSURANCE_TYPE.toString()))
            .andExpect(jsonPath("$.referred").value(DEFAULT_REFERRED.toString()))
            .andExpect(jsonPath("$.kinshipName").value(DEFAULT_KINSHIP_NAME.toString()))
            .andExpect(jsonPath("$.kinship").value(DEFAULT_KINSHIP.toString()))
            .andExpect(jsonPath("$.kinshipAddress").value(DEFAULT_KINSHIP_ADDRESS.toString()))
            .andExpect(jsonPath("$.kinshipPhone").value(DEFAULT_KINSHIP_PHONE.toString()))
            .andExpect(jsonPath("$.neighborhood").value(DEFAULT_NEIGHBORHOOD.toString()))
            .andExpect(jsonPath("$.parroquia").value(DEFAULT_PARROQUIA.toString()))
            .andExpect(jsonPath("$.canton").value(DEFAULT_CANTON.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.toString()))
            .andExpect(jsonPath("$.culturalGroup").value(DEFAULT_CULTURAL_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

		int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        person.setSystemInstitution(UPDATED_SYSTEM_INSTITUTION);
        person.setOperatingUnit(UPDATED_OPERATING_UNIT);
        person.setOperatingUnitCode(UPDATED_OPERATING_UNIT_CODE);
        person.setPersonType(UPDATED_PERSON_TYPE);
        person.setPersonFaculty(UPDATED_PERSON_FACULTY);
        person.setPersonCareer(UPDATED_PERSON_CAREER);
        person.setPersonSemester(UPDATED_PERSON_SEMESTER);
        person.setFirstName(UPDATED_FIRST_NAME);
        person.setSecondName(UPDATED_SECOND_NAME);
        person.setPaternalSurname(UPDATED_PATERNAL_SURNAME);
        person.setMaternalSurname(UPDATED_MATERNAL_SURNAME);
        person.setCedula(UPDATED_CEDULA);
        person.setAddress(UPDATED_ADDRESS);
        person.setPhone(UPDATED_PHONE);
        person.setBirthDate(UPDATED_BIRTH_DATE);
        person.setBirthPlace(UPDATED_BIRTH_PLACE);
        person.setNationality(UPDATED_NATIONALITY);
        person.setAge(UPDATED_AGE);
        person.setGender(UPDATED_GENDER);
        person.setMaritalStatus(UPDATED_MARITAL_STATUS);
        person.setApprovedInstructionYear(UPDATED_APPROVED_INSTRUCTION_YEAR);
        person.setAdmissionDate(UPDATED_ADMISSION_DATE);
        person.setOccupation(UPDATED_OCCUPATION);
        person.setCompanyWork(UPDATED_COMPANY_WORK);
        person.setInsuranceType(UPDATED_INSURANCE_TYPE);
        person.setReferred(UPDATED_REFERRED);
        person.setKinshipName(UPDATED_KINSHIP_NAME);
        person.setKinship(UPDATED_KINSHIP);
        person.setKinshipAddress(UPDATED_KINSHIP_ADDRESS);
        person.setKinshipPhone(UPDATED_KINSHIP_PHONE);
        person.setNeighborhood(UPDATED_NEIGHBORHOOD);
        person.setParroquia(UPDATED_PARROQUIA);
        person.setCanton(UPDATED_CANTON);
        person.setProvincia(UPDATED_PROVINCIA);
        person.setZone(UPDATED_ZONE);
        person.setCulturalGroup(UPDATED_CULTURAL_GROUP);
        

        restPersonMockMvc.perform(put("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = persons.get(persons.size() - 1);
        assertThat(testPerson.getSystemInstitution()).isEqualTo(UPDATED_SYSTEM_INSTITUTION);
        assertThat(testPerson.getOperatingUnit()).isEqualTo(UPDATED_OPERATING_UNIT);
        assertThat(testPerson.getOperatingUnitCode()).isEqualTo(UPDATED_OPERATING_UNIT_CODE);
        assertThat(testPerson.getPersonType()).isEqualTo(UPDATED_PERSON_TYPE);
        assertThat(testPerson.getPersonFaculty()).isEqualTo(UPDATED_PERSON_FACULTY);
        assertThat(testPerson.getPersonCareer()).isEqualTo(UPDATED_PERSON_CAREER);
        assertThat(testPerson.getPersonSemester()).isEqualTo(UPDATED_PERSON_SEMESTER);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testPerson.getPaternalSurname()).isEqualTo(UPDATED_PATERNAL_SURNAME);
        assertThat(testPerson.getMaternalSurname()).isEqualTo(UPDATED_MATERNAL_SURNAME);
        assertThat(testPerson.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testPerson.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPerson.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPerson.getApprovedInstructionYear()).isEqualTo(UPDATED_APPROVED_INSTRUCTION_YEAR);
        assertThat(testPerson.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
        assertThat(testPerson.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPerson.getCompanyWork()).isEqualTo(UPDATED_COMPANY_WORK);
        assertThat(testPerson.getInsuranceType()).isEqualTo(UPDATED_INSURANCE_TYPE);
        assertThat(testPerson.getReferred()).isEqualTo(UPDATED_REFERRED);
        assertThat(testPerson.getKinshipName()).isEqualTo(UPDATED_KINSHIP_NAME);
        assertThat(testPerson.getKinship()).isEqualTo(UPDATED_KINSHIP);
        assertThat(testPerson.getKinshipAddress()).isEqualTo(UPDATED_KINSHIP_ADDRESS);
        assertThat(testPerson.getKinshipPhone()).isEqualTo(UPDATED_KINSHIP_PHONE);
        assertThat(testPerson.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
        assertThat(testPerson.getParroquia()).isEqualTo(UPDATED_PARROQUIA);
        assertThat(testPerson.getCanton()).isEqualTo(UPDATED_CANTON);
        assertThat(testPerson.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testPerson.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testPerson.getCulturalGroup()).isEqualTo(UPDATED_CULTURAL_GROUP);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

		int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/persons/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
