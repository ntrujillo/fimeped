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
import org.uce.fimeped.domain.CurrentIllness;
import org.uce.fimeped.repository.CurrentIllnessRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing CurrentIllness.
 */
@RestController
@RequestMapping("/api")
public class CurrentIllnessResource {

	private final Logger log = LoggerFactory.getLogger(CurrentIllnessResource.class);

	@Inject
	private CurrentIllnessRepository currentIllnessRepository;

	/**
	 * POST /currentIllnesss -> Create a new currentIllness.
	 */
	@RequestMapping(value = "/currentIllnesss", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> create(@RequestBody CurrentIllness currentIllness) throws URISyntaxException {
		log.debug("REST request to save CurrentIllness : {}", currentIllness);
		if (currentIllness.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new currentIllness cannot already have an ID")
					.body(null);
		}
		CurrentIllness result = currentIllnessRepository.save(currentIllness);
		return ResponseEntity.created(new URI("/api/currentIllnesss/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("currentIllness", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /currentIllnesss -> Updates an existing currentIllness.
	 */
	@RequestMapping(value = "/currentIllnesss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> update(@RequestBody CurrentIllness currentIllness) throws URISyntaxException {
		log.debug("REST request to update CurrentIllness : {}", currentIllness);
		if (currentIllness.getId() == null) {
			return create(currentIllness);
		}
		CurrentIllness result = currentIllnessRepository.save(currentIllness);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("currentIllness", currentIllness.getId().toString()))
				.body(result);
	}

	/**
	 * GET /currentIllnesss -> get all the currentIllnesss.
	 */
	@RequestMapping(value = "/currentIllnesss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CurrentIllness>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

		Page<CurrentIllness> page;		
		page = currentIllnessRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/currentIllnesss", offset, limit);
		return new ResponseEntity<List<CurrentIllness>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /currentIllnesss/:id -> get the "id" currentIllness.
	 */
	@RequestMapping(value = "/currentIllnesss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get CurrentIllness : {}", id);
		CurrentIllness currentIllness = currentIllnessRepository.findOne(id);
		if (currentIllness == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentIllness, HttpStatus.OK);
	}

	/**
	 * DELETE /currentIllnesss/:id -> delete the "id" currentIllness.
	 */
	@RequestMapping(value = "/currentIllnesss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete CurrentIllness : {}", id);
		currentIllnessRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currentIllness", id.toString()))
				.build();
	}

	/** CurrentIllness by Episode **/
	
	

	/**
	 * POST /currentIllnesss -> Create a new currentIllness.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentIllnesss", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> createByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody CurrentIllness currentIllness)
			throws URISyntaxException {
		log.debug("REST request to save CurrentIllness : {}", currentIllness);
		if (currentIllness.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new currentIllness cannot already have an ID")
					.body(null);
		}
		CurrentIllness result = currentIllnessRepository.save(currentIllness);
		return ResponseEntity.created(new URI("/api/episodes/"+episodeId+"/currentIllnesss/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("currentIllness", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /currentIllnesss -> Updates an existing currentIllness.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentIllnesss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody CurrentIllness currentIllness)
			throws URISyntaxException {
		log.debug("REST request to update CurrentIllness : {}", currentIllness);
		if (currentIllness.getId() == null) {
			return create(currentIllness);
		}
		CurrentIllness result = currentIllnessRepository.save(currentIllness);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("currentIllness", currentIllness.getId().toString()))
				.body(result);
	}

	/**
	 * GET /currentIllnesss -> get all the currentIllnesss.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentIllnesss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CurrentIllness>> getAllByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

		Page<CurrentIllness> page;	
		page = currentIllnessRepository.findByEpisode(episodeId, PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes/"+episodeId+"/currentIllnesss", offset, limit);
		return new ResponseEntity<List<CurrentIllness>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /currentIllnesss/:id -> get the "id" currentIllness.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentIllnesss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentIllness> getByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get CurrentIllness : {}", id);
		CurrentIllness currentIllness = currentIllnessRepository.findOneByEpisode(episodeId, id);
		if (currentIllness == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentIllness, HttpStatus.OK);
	}

	/**
	 * DELETE /currentIllnesss/:id -> delete the "id" currentIllness.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentIllnesss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete CurrentIllness : {}", id);
		currentIllnessRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currentIllness", id.toString()))
				.build();
	}
}
