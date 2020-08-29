package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.uce.fimeped.domain.Diagnostic;
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.repository.DiagnosticRepository;
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
 * REST controller for managing Diagnostic.
 */
@RestController
@RequestMapping("/api")
public class DiagnosticResource {

	private final Logger log = LoggerFactory.getLogger(DiagnosticResource.class);

	@Inject
	private DiagnosticRepository diagnosticRepository;

	/**
	 * POST /diagnostics -> Create a new diagnostic.
	 */
	@RequestMapping(value = "/diagnostics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> create(@RequestBody Diagnostic diagnostic) throws URISyntaxException {
		log.debug("REST request to save Diagnostic : {}", diagnostic);
		if (diagnostic.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new diagnostic cannot already have an ID")
					.body(null);
		}
		Diagnostic result = diagnosticRepository.save(diagnostic);
		return ResponseEntity.created(new URI("/api/diagnostics/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("diagnostic", result.getId().toString())).body(result);
	}

	/**
	 * PUT /diagnostics -> Updates an existing diagnostic.
	 */
	@RequestMapping(value = "/diagnostics", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> update(@RequestBody Diagnostic diagnostic) throws URISyntaxException {
		log.debug("REST request to update Diagnostic : {}", diagnostic);
		if (diagnostic.getId() == null) {
			return create(diagnostic);
		}
		Diagnostic result = diagnosticRepository.save(diagnostic);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("diagnostic", diagnostic.getId().toString())).body(result);
	}

	/**
	 * GET /diagnostics -> get all the diagnostics.
	 */
	@RequestMapping(value = "/diagnostics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Diagnostic>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<Diagnostic> page;		
		page = diagnosticRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diagnostics", offset, limit);
		return new ResponseEntity<List<Diagnostic>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /diagnostics/:id -> get the "id" diagnostic.
	 */
	@RequestMapping(value = "/diagnostics/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Diagnostic : {}", id);
		Diagnostic diagnostic = diagnosticRepository.findOne(id);
		if (diagnostic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(diagnostic, HttpStatus.OK);
	}

	/**
	 * DELETE /diagnostics/:id -> delete the "id" diagnostic.
	 */
	@RequestMapping(value = "/diagnostics/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete Diagnostic : {}", id);
		diagnosticRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("diagnostic", id.toString())).build();
	}

	/** Diagnostic by Episode **/

	/**
	 * POST /diagnostics -> Create a new diagnostic.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/diagnostics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> createByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody Diagnostic diagnostic) throws URISyntaxException {
		log.debug("REST request to save Diagnostic : {}", diagnostic);
		if (diagnostic.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new diagnostic cannot already have an ID")
					.body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		diagnostic.setEpisode(episode);
		Diagnostic result = diagnosticRepository.save(diagnostic);
		return ResponseEntity.created(new URI("/api/episodes/"+episodeId+"/diagnostics/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("diagnostic", result.getId().toString())).body(result);
	}

	/**
	 * PUT /diagnostics -> Updates an existing diagnostic.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/diagnostics", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody Diagnostic diagnostic) throws URISyntaxException {
		log.debug("REST request to update Diagnostic : {}", diagnostic);
		if (diagnostic.getId() == null) {
			return create(diagnostic);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		diagnostic.setEpisode(episode);
		Diagnostic result = diagnosticRepository.save(diagnostic);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("diagnostic", diagnostic.getId().toString())).body(result);
	}

	/**
	 * GET /diagnostics -> get all the diagnostics.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/diagnostics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Diagnostic>> getAllByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<Diagnostic> page;		
		page = diagnosticRepository.findByEpisode(episodeId, PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes/"+episodeId+"/diagnostics", offset, limit);
		return new ResponseEntity<List<Diagnostic>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /diagnostics/:id -> get the "id" diagnostic.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/diagnostics/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Diagnostic> getByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Diagnostic : {}", id);
		Diagnostic diagnostic = diagnosticRepository.findOneByEpisode(episodeId, id);
		if (diagnostic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(diagnostic, HttpStatus.OK);
	}

	/**
	 * DELETE /diagnostics/:id -> delete the "id" diagnostic.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/diagnostics/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete Diagnostic : {}", id);
		diagnosticRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("diagnostic", id.toString())).build();
	}
}
