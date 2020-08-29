package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.FamilyHistory;
import org.uce.fimeped.repository.FamilyHistoryRepository;
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
 * REST controller for managing FamilyHistory.
 */
@RestController
@RequestMapping("/api")
public class FamilyHistoryResource {

	private final Logger log = LoggerFactory.getLogger(FamilyHistoryResource.class);

	@Inject
	private FamilyHistoryRepository familyHistoryRepository;

	/**
	 * POST /familyHistorys -> Create a new familyHistory.
	 */
	@RequestMapping(value = "/familyHistorys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> create(@RequestBody FamilyHistory familyHistory) throws URISyntaxException {
		log.debug("REST request to save FamilyHistory : {}", familyHistory);
		if (familyHistory.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new familyHistory cannot already have an ID")
					.body(null);
		}
		FamilyHistory result = familyHistoryRepository.save(familyHistory);
		return ResponseEntity.created(new URI("/api/familyHistorys/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("familyHistory", result.getId().toString())).body(result);
	}

	/**
	 * PUT /familyHistorys -> Updates an existing familyHistory.
	 */
	@RequestMapping(value = "/familyHistorys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> update(@RequestBody FamilyHistory familyHistory) throws URISyntaxException {
		log.debug("REST request to update FamilyHistory : {}", familyHistory);
		if (familyHistory.getId() == null) {
			return create(familyHistory);
		}
		FamilyHistory result = familyHistoryRepository.save(familyHistory);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("familyHistory", familyHistory.getId().toString()))
				.body(result);
	}

	/**
	 * GET /familyHistorys -> get all the familyHistorys.
	 */
	@RequestMapping(value = "/familyHistorys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<FamilyHistory>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<FamilyHistory> page = familyHistoryRepository.findAll(
				PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/familyHistorys", offset, limit);
		return new ResponseEntity<List<FamilyHistory>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /familyHistorys/:id -> get the "id" familyHistory.
	 */
	@RequestMapping(value = "/familyHistorys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get FamilyHistory : {}", id);
		FamilyHistory familyHistory = familyHistoryRepository.findOne(id);
		if (familyHistory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(familyHistory, HttpStatus.OK);
	}

	/**
	 * DELETE /familyHistorys/:id -> delete the "id" familyHistory.
	 */
	@RequestMapping(value = "/familyHistorys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete FamilyHistory : {}", id);
		familyHistoryRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("familyHistory", id.toString()))
				.build();
	}
	
	
	

	/** FamilyHistory by ClinicHistory **/

	/**
	 * POST /familyHistorys -> Create a new familyHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/familyHistorys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> createByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestBody FamilyHistory familyHistory) throws URISyntaxException {
		log.debug("REST request to save FamilyHistory : {}", familyHistory);
		if (familyHistory.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new familyHistory cannot already have an ID")
					.body(null);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		familyHistory.setClinicHistory(clinicHistory);
		FamilyHistory result = familyHistoryRepository.save(familyHistory);
		return ResponseEntity.created(new URI("/api/clinicHistorys/"+historyId+"/familyHistorys/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("familyHistory", result.getId().toString())).body(result);
	}

	/**
	 * PUT /familyHistorys -> Updates an existing familyHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/familyHistorys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> updateByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestBody FamilyHistory familyHistory) throws URISyntaxException {
		log.debug("REST request to update FamilyHistory : {}", familyHistory);
		if (familyHistory.getId() == null) {
			return create(familyHistory);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		familyHistory.setClinicHistory(clinicHistory);
		FamilyHistory result = familyHistoryRepository.save(familyHistory);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("familyHistory", familyHistory.getId().toString()))
				.body(result);
	}

	/**
	 * GET /familyHistorys -> get all the familyHistorys.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/familyHistorys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<FamilyHistory>> getAllByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<FamilyHistory> page = familyHistoryRepository.findByClinicHistory(historyId,
				PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinicHistorys/"+historyId+"/familyHistorys", offset, limit);
		return new ResponseEntity<List<FamilyHistory>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /familyHistorys/:id -> get the "id" familyHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/familyHistorys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<FamilyHistory> getByClinicHistory(
			@PathVariable(value = "history_id") Long historyId, @PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get FamilyHistory : {}", id);
		FamilyHistory familyHistory = familyHistoryRepository.findOneByClinicHistory(historyId, id);
		if (familyHistory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(familyHistory, HttpStatus.OK);
	}

	/**
	 * DELETE /familyHistorys/:id -> delete the "id" familyHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/familyHistorys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByClinicHistory(
			@PathVariable(value = "history_id") Long historyId, @PathVariable Long id) {
		log.debug("REST request to delete FamilyHistory : {}", id);
		familyHistoryRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("familyHistory", id.toString()))
				.build();
	}
}
