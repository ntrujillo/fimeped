package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.PersonalHistory;
import org.uce.fimeped.repository.PersonalHistoryRepository;
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
 * REST controller for managing PersonalHistory.
 */
@RestController
@RequestMapping("/api")
public class PersonalHistoryResource {

	private final Logger log = LoggerFactory.getLogger(PersonalHistoryResource.class);

	@Inject
	private PersonalHistoryRepository personalHistoryRepository;

	/**
	 * POST /personalHistorys -> Create a new personalHistory.
	 */
	@RequestMapping(value = "/personalHistorys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> create(@RequestBody PersonalHistory personalHistory)
			throws URISyntaxException {
		log.debug("REST request to save PersonalHistory : {}", personalHistory);
		if (personalHistory.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new personalHistory cannot already have an ID")
					.body(null);
		}
		PersonalHistory result = personalHistoryRepository.save(personalHistory);
		return ResponseEntity.created(new URI("/api/personalHistorys/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("personalHistory", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /personalHistorys -> Updates an existing personalHistory.
	 */
	@RequestMapping(value = "/personalHistorys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> update(@RequestBody PersonalHistory personalHistory)
			throws URISyntaxException {
		log.debug("REST request to update PersonalHistory : {}", personalHistory);
		if (personalHistory.getId() == null) {
			return create(personalHistory);
		}
		PersonalHistory result = personalHistoryRepository.save(personalHistory);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("personalHistory", personalHistory.getId().toString()))
				.body(result);
	}

	/**
	 * GET /personalHistorys -> get all the personalHistorys.
	 */
	@RequestMapping(value = "/personalHistorys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<PersonalHistory>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<PersonalHistory> page = personalHistoryRepository
				.findAll(PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personalHistorys", offset,
				limit);
		return new ResponseEntity<List<PersonalHistory>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /personalHistorys/:id -> get the "id" personalHistory.
	 */
	@RequestMapping(value = "/personalHistorys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get PersonalHistory : {}", id);
		PersonalHistory personalHistory = personalHistoryRepository.findOne(id);
		if (personalHistory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(personalHistory, HttpStatus.OK);
	}

	/**
	 * DELETE /personalHistorys/:id -> delete the "id" personalHistory.
	 */
	@RequestMapping(value = "/personalHistorys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete PersonalHistory : {}", id);
		personalHistoryRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personalHistory", id.toString()))
				.build();
	}

	/** PersonalHistory by ClinicHistory **/

	/**
	 * POST /personalHistorys -> Create a new personalHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/personalHistorys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> createByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestBody PersonalHistory personalHistory) throws URISyntaxException {
		log.debug("REST request to save PersonalHistory : {}", personalHistory);
		if (personalHistory.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new personalHistory cannot already have an ID")
					.body(null);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		personalHistory.setClinicHistory(clinicHistory);
		PersonalHistory result = personalHistoryRepository.save(personalHistory);
		return ResponseEntity
				.created(new URI("/api/clinicHistorys/" + historyId + "/personalHistorys/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("personalHistory", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /personalHistorys -> Updates an existing personalHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/personalHistorys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> updateByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestBody PersonalHistory personalHistory) throws URISyntaxException {
		log.debug("REST request to update PersonalHistory : {}", personalHistory);
		if (personalHistory.getId() == null) {
			return create(personalHistory);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		personalHistory.setClinicHistory(clinicHistory);
		PersonalHistory result = personalHistoryRepository.save(personalHistory);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("personalHistory", personalHistory.getId().toString()))
				.body(result);
	}

	/**
	 * GET /personalHistorys -> get all the personalHistorys.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/personalHistorys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<PersonalHistory>> getAllByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<PersonalHistory> page = personalHistoryRepository.findByClinicHistory(historyId,
				PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/clinicHistorys/" + historyId + "/personalHistorys", offset, limit);
		return new ResponseEntity<List<PersonalHistory>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /personalHistorys/:id -> get the "id" personalHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/personalHistorys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PersonalHistory> getByClinicHistory(
			@PathVariable(value = "history_id") Long historyId, @PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get PersonalHistory : {}", id);
		PersonalHistory personalHistory = personalHistoryRepository.findOneByClinicHistory(historyId, id);
		if (personalHistory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(personalHistory, HttpStatus.OK);
	}

	/**
	 * DELETE /personalHistorys/:id -> delete the "id" personalHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/personalHistorys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByClinicHistory(
			@PathVariable(value = "history_id") Long historyId, @PathVariable Long id) {
		log.debug("REST request to delete PersonalHistory : {}", id);
		personalHistoryRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personalHistory", id.toString()))
				.build();
	}

}
