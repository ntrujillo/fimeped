package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.CurrentRevision;
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.repository.CurrentRevisionRepository;
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
 * REST controller for managing CurrentRevision.
 */
@RestController
@RequestMapping("/api")
public class CurrentRevisionResource {

	private final Logger log = LoggerFactory.getLogger(CurrentRevisionResource.class);

	@Inject
	private CurrentRevisionRepository currentRevisionRepository;

	/**
	 * POST /currentRevisions -> Create a new currentRevision.
	 */
	@RequestMapping(value = "/currentRevisions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> create(@RequestBody CurrentRevision currentRevision)
			throws URISyntaxException {
		log.debug("REST request to save CurrentRevision : {}", currentRevision);
		if (currentRevision.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new currentRevision cannot already have an ID")
					.body(null);
		}
		CurrentRevision result = currentRevisionRepository.save(currentRevision);
		return ResponseEntity.created(new URI("/api/currentRevisions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("currentRevision", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /currentRevisions -> Updates an existing currentRevision.
	 */
	@RequestMapping(value = "/currentRevisions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> update(@RequestBody CurrentRevision currentRevision)
			throws URISyntaxException {
		log.debug("REST request to update CurrentRevision : {}", currentRevision);
		if (currentRevision.getId() == null) {
			return create(currentRevision);
		}
		CurrentRevision result = currentRevisionRepository.save(currentRevision);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("currentRevision", currentRevision.getId().toString()))
				.body(result);
	}

	/**
	 * GET /currentRevisions -> get all the currentRevisions.
	 */
	@RequestMapping(value = "/currentRevisions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CurrentRevision>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

		Page<CurrentRevision> page;		
		page = currentRevisionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/currentRevisions", offset,
				limit);
		return new ResponseEntity<List<CurrentRevision>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /currentRevisions/:id -> get the "id" currentRevision.
	 */
	@RequestMapping(value = "/currentRevisions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get CurrentRevision : {}", id);
		CurrentRevision currentRevision = currentRevisionRepository.findOne(id);
		if (currentRevision == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentRevision, HttpStatus.OK);
	}

	/**
	 * DELETE /currentRevisions/:id -> delete the "id" currentRevision.
	 */
	@RequestMapping(value = "/currentRevisions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete CurrentRevision : {}", id);
		currentRevisionRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currentRevision", id.toString()))
				.build();
	}

	/** CurrentRevision by Episode **/

	/**
	 * POST /currentRevisions -> Create a new currentRevision.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentRevisions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> createByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody CurrentRevision currentRevision) throws URISyntaxException {
		log.debug("REST request to save CurrentRevision : {}", currentRevision);
		if (currentRevision.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new currentRevision cannot already have an ID")
					.body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		currentRevision.setEpisode(episode);
		CurrentRevision result = currentRevisionRepository.save(currentRevision);
		return ResponseEntity.created(new URI("/api/episodes/" + episodeId + "/currentRevisions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("currentRevision", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /currentRevisions -> Updates an existing currentRevision.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentRevisions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody CurrentRevision currentRevision) throws URISyntaxException {
		log.debug("REST request to update CurrentRevision : {}", currentRevision);
		if (currentRevision.getId() == null) {
			return create(currentRevision);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		currentRevision.setEpisode(episode);
		CurrentRevision result = currentRevisionRepository.save(currentRevision);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("currentRevision", currentRevision.getId().toString()))
				.body(result);
	}

	/**
	 * GET /currentRevisions -> get all the currentRevisions.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentRevisions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CurrentRevision>> getAllByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

		Page<CurrentRevision> page;
		
			page = currentRevisionRepository.findByEpisode(episodeId,
					PaginationUtil.generatePageRequest(offset, limit));
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/episodes/" + episodeId + "/currentRevisions", offset, limit);
		return new ResponseEntity<List<CurrentRevision>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /currentRevisions/:id -> get the "id" currentRevision.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentRevisions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<CurrentRevision> getByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get CurrentRevision : {}", id);
		CurrentRevision currentRevision = currentRevisionRepository.findOneByEpisode(episodeId, id);
		if (currentRevision == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentRevision, HttpStatus.OK);
	}

	/**
	 * DELETE /currentRevisions/:id -> delete the "id" currentRevision.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/currentRevisions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete CurrentRevision : {}", id);
		currentRevisionRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currentRevision", id.toString()))
				.build();
	}
}
