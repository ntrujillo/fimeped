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
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.Reason;
import org.uce.fimeped.repository.ReasonRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Reason.
 */
@RestController
@RequestMapping("/api")
public class ReasonResource {

	private final Logger log = LoggerFactory.getLogger(ReasonResource.class);

	@Inject
	private ReasonRepository reasonRepository;

	/**
	 * POST /reasons -> Create a new reason.
	 */
	@RequestMapping(value = "/reasons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> create(@RequestBody Reason reason) throws URISyntaxException {
		log.debug("REST request to save Reason : {}", reason);
		if (reason.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new reason cannot already have an ID").body(null);
		}
		Reason result = reasonRepository.save(reason);
		return ResponseEntity.created(new URI("/api/reasons/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("reason", result.getId().toString())).body(result);
	}

	/**
	 * PUT /reasons -> Updates an existing reason.
	 */
	@RequestMapping(value = "/reasons", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> update(@RequestBody Reason reason) throws URISyntaxException {
		log.debug("REST request to update Reason : {}", reason);
		if (reason.getId() == null) {
			return create(reason);
		}
		Reason result = reasonRepository.save(reason);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("reason", reason.getId().toString()))
				.body(result);
	}

	/**
	 * GET /reasons -> get all the reasons.
	 */
	@RequestMapping(value = "/reasons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Reason>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

		Page<Reason> page;

		page = reasonRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reasons", offset, limit);
		return new ResponseEntity<List<Reason>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /reasons/:id -> get the "id" reason.
	 */
	@RequestMapping(value = "/reasons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Reason : {}", id);
		Reason reason = reasonRepository.findOne(id);
		if (reason == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reason, HttpStatus.OK);
	}

	/**
	 * DELETE /reasons/:id -> delete the "id" reason.
	 */
	@RequestMapping(value = "/reasons/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete Reason : {}", id);
		reasonRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reason", id.toString())).build();
	}

	/** Reasons by Episode **/

	/**
	 * POST /episodes/:episode_id/reasons -> Create a new reason.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/reasons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> createByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody Reason reason)
			throws URISyntaxException {
		log.debug("REST request to save Reason episode: {} reason {}", episodeId, reason);
		if (reason.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new reason cannot already have an ID").body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		reason.setEpisode(episode);
		Reason result = reasonRepository.save(reason);
		return ResponseEntity.created(new URI("/api/episodes/" + episodeId + "reasons/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("reason", result.getId().toString())).body(result);
	}

	/**
	 * PUT /episodes/:episode_id/reasons -> Updates an existing reason.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/reasons", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody Reason reason)
			throws URISyntaxException {
		log.debug("REST request to update Reason episode: {} reason {}", episodeId, reason);
		if (reason.getId() == null) {
			return create(reason);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		reason.setEpisode(episode);
		Reason result = reasonRepository.save(reason);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("reason", reason.getId().toString()))
				.body(result);
	}

	/**
	 * GET /episodes/:episode_id/reasons -> get all the reasons.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/reasons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Reason>> getAllByEpisode(
			@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		log.debug("REST request to get Reasons episode: {}", episodeId);
		Page<Reason> page;
		page = reasonRepository.findByEpisode(episodeId, PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes/"+episodeId+"/reasons", offset, limit);
		return new ResponseEntity<List<Reason>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /episodes/:episode_id/reasons/:id -> get the "id" reason.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/reasons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reason> getByEpisode(@PathVariable(value = "episode_id") Long episodeId, @PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get Reason episode: {} reason {}", episodeId, id);
		Reason reason = reasonRepository.findOneByEpisode(episodeId, id);
		if (reason == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reason, HttpStatus.OK);
	}

	/**
	 * DELETE /episodes/:episode_id/reasons/:id -> delete the "id" reason.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/reasons/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId, @PathVariable Long id) {
		log.debug("REST request to delete Reason episode: {} reason {}", episodeId, id);
		reasonRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reason", id.toString())).build();
	}
}
