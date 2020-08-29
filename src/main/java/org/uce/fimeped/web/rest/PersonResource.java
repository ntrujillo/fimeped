package org.uce.fimeped.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.Person;
import org.uce.fimeped.repository.ClinicHistoryRepository;
import org.uce.fimeped.repository.PersonRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    private PersonRepository personRepository;
    
    @Inject 
    private ClinicHistoryRepository historyRepository;

    /**
     * POST  /persons -> Create a new person.
     */
    @RequestMapping(value = "/persons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> create(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new person cannot already have an ID").body(null);
        }
        Person result = personRepository.save(person);
        return ResponseEntity.created(new URI("/api/persons/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("person", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /persons -> Updates an existing person.
     */
    @RequestMapping(value = "/persons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> update(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            return create(person);
        }
        Person result = personRepository.save(person);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("person", person.getId().toString()))
                .body(result);
    }

    /**
     * GET  /persons -> get all the persons.
     */
    @RequestMapping(value = "/persons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Person>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "secondName", required = false) String secondName,
			@RequestParam(value = "paternalSurname", required = false) String paternalSurname,
			@RequestParam(value = "maternalSurname", required = false) String maternalSurname,
			@RequestParam(value = "cedula", required = false) String cedula) {
        log.debug("REST request to get all Persons");        
        firstName = firstName == null ? "%" : "%"+firstName+"%";
		secondName = secondName == null ? "%" : "%"+secondName+"%";
		paternalSurname = paternalSurname == null ? "%" : "%"+paternalSurname+"%";
		maternalSurname = maternalSurname == null ? "%" : "%"+maternalSurname+"%";
		cedula = cedula == null ? "%" : cedula+"%";
		Page<Person> page ;
		HttpHeaders headers;
		try {
			page =personRepository.findByFilter(firstName, secondName, paternalSurname,
					maternalSurname, cedula, PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
			headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persons", offset, limit);
			return new ResponseEntity<List<Person>>(page.getContent(), headers, HttpStatus.OK);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			log.debug("Exception",e);
			return ResponseEntity.badRequest().header("Failure", e.getMessage())
					.body(null);
		}
		
    }
    
    
    /**
     * GET  /persons -> get all the clinicHistory by person.
     */
    @RequestMapping(value = "/persons/{person_id}/clinicHistorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ClinicHistory>> getPersonClinicHistory(@PathVariable(value = "person_id") Long personId) {
        log.debug("REST request to get all Persons");       
       List<ClinicHistory> clinicHistorys;
		try {
			clinicHistorys =historyRepository.findByPerson(personId);			
			return new ResponseEntity<List<ClinicHistory>>(clinicHistorys,  HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("Exception",e);
			return new ResponseEntity<List<ClinicHistory>>(new ArrayList<ClinicHistory>(),  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }

    /**
     * GET  /persons/:id -> get the "id" person.
     */
    @RequestMapping(value = "/persons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /**
     * DELETE  /persons/:id -> delete the "id" person.
     */
    @RequestMapping(value = "/persons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("person", id.toString())).build();
    }
}
