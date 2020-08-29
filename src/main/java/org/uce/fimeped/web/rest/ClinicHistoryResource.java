package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.repository.ClinicHistoryRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing ClinicHistory.
 */
@RestController
@RequestMapping("/api")
public class ClinicHistoryResource {

	private final Logger log = LoggerFactory.getLogger(ClinicHistoryResource.class);

	@Inject
	private ClinicHistoryRepository clinicHistoryRepository;

	/**
	 * POST /clinicHistorys -> Create a new clinicHistory.
	 */
	@RequestMapping(value = "/clinicHistorys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ClinicHistory> create(@RequestBody ClinicHistory clinicHistory) throws URISyntaxException {
		log.debug("REST request to save ClinicHistory : {}", clinicHistory);
		if (clinicHistory.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new clinicHistory cannot already have an ID")
					.body(null);
		}
		ClinicHistory result = clinicHistoryRepository.save(clinicHistory);
		return ResponseEntity.created(new URI("/api/clinicHistorys/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("clinicHistory", result.getId().toString())).body(result);
	}

	/**
	 * PUT /clinicHistorys -> Updates an existing clinicHistory.
	 */
	@RequestMapping(value = "/clinicHistorys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ClinicHistory> update(@RequestBody ClinicHistory clinicHistory) throws URISyntaxException {
		log.debug("REST request to update ClinicHistory : {}", clinicHistory);
		if (clinicHistory.getId() == null) {
			return create(clinicHistory);
		}
		ClinicHistory result = clinicHistoryRepository.save(clinicHistory);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("clinicHistory", clinicHistory.getId().toString()))
				.body(result);
	}

	/**
	 * GET /clinicHistorys -> get all the clinicHistorys.
	 * 
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/clinicHistorys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ClinicHistory>> getAllByFilter(
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "secondName", required = false) String secondName,
			@RequestParam(value = "paternalSurname", required = false) String paternalSurname,
			@RequestParam(value = "maternalSurname", required = false) String maternalSurname,
			@RequestParam(value = "cedula", required = false) String cedula) throws URISyntaxException {
		log.debug("REST request to get all ClinicHistorys");
		firstName = firstName == null ? "%" : "%"+firstName+"%";
		secondName = secondName == null ? "%" : "%"+secondName+"%";
		paternalSurname = paternalSurname == null ? "%" : "%"+paternalSurname+"%";
		maternalSurname = maternalSurname == null ? "%" : "%"+maternalSurname+"%";
		cedula = cedula == null ? "%" : cedula+"%";

		Page<ClinicHistory> page = clinicHistoryRepository.findByFilter(firstName, secondName, paternalSurname,
				maternalSurname, cedula, PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinicHistorys", offset, limit);
		return new ResponseEntity<List<ClinicHistory>>(page.getContent(), headers, HttpStatus.OK);

	}
	
	
	/**
	 * GET /clinicHistorys/:id -> get the "id" clinicHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ClinicHistory> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get ClinicHistory : {}", id);
		ClinicHistory clinicHistory = clinicHistoryRepository.findOne(id);
		if (clinicHistory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(clinicHistory, HttpStatus.OK);
	}

	/**
	 * DELETE /clinicHistorys/:id -> delete the "id" clinicHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete ClinicHistory : {}", id);
		clinicHistoryRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clinicHistory", id.toString()))
				.build();
	}
}
