package org.uce.fimeped.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import org.uce.fimeped.domain.VitalSign;
import org.uce.fimeped.repository.VitalSignRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing VitalSign.
 */
@RestController
@RequestMapping("/api")
public class VitalSignResource {

	private final Logger log = LoggerFactory.getLogger(VitalSignResource.class);

	@Inject
	private VitalSignRepository vitalSignRepository;

	

	/** VitalSigns by ClinicHistory **/

	/**
	 * POST /vitalSigns -> Create a new vitalSign.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/vitalSigns", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<VitalSign> createByClinicHistory(@PathVariable(value = "history_id") Long historyId, @RequestBody VitalSign vitalSign) throws URISyntaxException {
		log.debug("REST request to save VitalSign episode: {} VitalSign {}",historyId, vitalSign);
		if (vitalSign.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new vitalSign cannot already have an ID")
					.body(null);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		vitalSign.setClinicHistory(clinicHistory);
		VitalSign result = vitalSignRepository.save(vitalSign);
		return ResponseEntity.created(new URI("/api/clinicHistorys/"+historyId+"/vitalSigns/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("vitalSign", result.getId().toString())).body(result);
	}

	/**
	 * PUT /vitalSigns -> Updates an existing vitalSign.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/vitalSigns", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<VitalSign> updateByClinicHistory(@PathVariable(value = "history_id") Long historyId, @RequestBody VitalSign vitalSign) throws URISyntaxException {
		log.debug("REST request to update VitalSign episode: {} VitalSign {}",historyId, vitalSign);
		if (vitalSign.getId() == null) {
			return createByClinicHistory(historyId, vitalSign);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		vitalSign.setClinicHistory(clinicHistory);
		VitalSign result = vitalSignRepository.save(vitalSign);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("vitalSign", vitalSign.getId().toString())).body(result);
	}

	/**
	 * GET /vitalSigns -> get all the vitalSigns.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/vitalSigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<VitalSign>> getAllByEpisode(@PathVariable(value = "history_id") Long historyId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort) throws URISyntaxException {
		log.debug("REST request to get VitalSign episode: {}",historyId);
		Page<VitalSign> page;		
		page = vitalSignRepository.findByClinicHistory(historyId, PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinicHistorys/"+historyId+"/vitalSigns", offset, limit);
		return new ResponseEntity<List<VitalSign>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /vitalSigns/:id -> get the "id" vitalSign.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/vitalSigns/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<VitalSign> getByEpisode(@PathVariable(value = "history_id") Long historyId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get VitalSign episode: {} VitalSign {}",historyId, id);
		VitalSign vitalSign = vitalSignRepository.findOneByClinicHistory(historyId, id);
		if (vitalSign == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(vitalSign, HttpStatus.OK);
	}

	/**
	 * DELETE /vitalSigns/:id -> delete the "id" vitalSign.
	 */
	@RequestMapping(value = "/clinicHistory/{history_id}/vitalSigns/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "history_id") Long historyId,
			@PathVariable Long id) {
		log.debug("REST request to delete VitalSign episode: {} vitalSign {}",historyId, id);
		vitalSignRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vitalSign", id.toString())).build();
	}
}
