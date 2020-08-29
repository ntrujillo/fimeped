package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.EvolutionPrescription;
import org.uce.fimeped.repository.EvolutionPrescriptionRepository;
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
 * REST controller for managing EvolutionPrescription.
 */
@RestController
@RequestMapping("/api")
public class EvolutionPrescriptionResource {

	private final Logger log = LoggerFactory.getLogger(EvolutionPrescriptionResource.class);

	@Inject
	private EvolutionPrescriptionRepository evolutionPrescriptionRepository;

	/**
	 * POST /evolutionPrescriptions -> Create a new evolutionPrescription.
	 */
	@RequestMapping(value = "/evolutionPrescriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> create(@RequestBody EvolutionPrescription evolutionPrescription)
			throws URISyntaxException {
		log.debug("REST request to save EvolutionPrescription : {}", evolutionPrescription);
		if (evolutionPrescription.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "A new evolutionPrescription cannot already have an ID").body(null);
		}
		EvolutionPrescription result = evolutionPrescriptionRepository.save(evolutionPrescription);
		return ResponseEntity.created(new URI("/api/evolutionPrescriptions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("evolutionPrescription", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /evolutionPrescriptions -> Updates an existing evolutionPrescription.
	 */
	@RequestMapping(value = "/evolutionPrescriptions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> update(@RequestBody EvolutionPrescription evolutionPrescription)
			throws URISyntaxException {
		log.debug("REST request to update EvolutionPrescription : {}", evolutionPrescription);
		if (evolutionPrescription.getId() == null) {
			return create(evolutionPrescription);
		}
		EvolutionPrescription result = evolutionPrescriptionRepository.save(evolutionPrescription);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert("evolutionPrescription", evolutionPrescription.getId().toString()))
				.body(result);
	}

	/**
	 * GET /evolutionPrescriptions -> get all the evolutionPrescriptions.
	 */
	@RequestMapping(value = "/evolutionPrescriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<EvolutionPrescription>> getAll(
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<EvolutionPrescription> page;		
		page = evolutionPrescriptionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evolutionPrescriptions", offset,
				limit);
		return new ResponseEntity<List<EvolutionPrescription>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /evolutionPrescriptions/:id -> get the "id" evolutionPrescription.
	 */
	@RequestMapping(value = "/evolutionPrescriptions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get EvolutionPrescription : {}", id);
		EvolutionPrescription evolutionPrescription = evolutionPrescriptionRepository.findOne(id);
		if (evolutionPrescription == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(evolutionPrescription, HttpStatus.OK);
	}

	/**
	 * DELETE /evolutionPrescriptions/:id -> delete the "id"
	 * evolutionPrescription.
	 */
	@RequestMapping(value = "/evolutionPrescriptions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete EvolutionPrescription : {}", id);
		evolutionPrescriptionRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evolutionPrescription", id.toString()))
				.build();
	}

	/** EvolutionPrescription by Episode **/
	

	/**
	 * POST /evolutionPrescriptions -> Create a new evolutionPrescription.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/evolutionPrescriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> createByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody EvolutionPrescription evolutionPrescription) throws URISyntaxException {
		log.debug("REST request to save EvolutionPrescription : {}", evolutionPrescription);
		if (evolutionPrescription.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "A new evolutionPrescription cannot already have an ID").body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		evolutionPrescription.setEpisode(episode);
		EvolutionPrescription result = evolutionPrescriptionRepository.save(evolutionPrescription);
		return ResponseEntity.created(new URI("/api/episodes/"+episodeId+"/evolutionPrescriptions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("evolutionPrescription", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /evolutionPrescriptions -> Updates an existing evolutionPrescription.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/evolutionPrescriptions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> updateByEpisode(
			@PathVariable(value = "episode_id") Long episodeId, @RequestBody EvolutionPrescription evolutionPrescription) throws URISyntaxException {
		log.debug("REST request to update EvolutionPrescription : {}", evolutionPrescription);
		if (evolutionPrescription.getId() == null) {
			return create(evolutionPrescription);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		evolutionPrescription.setEpisode(episode);
		EvolutionPrescription result = evolutionPrescriptionRepository.save(evolutionPrescription);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert("evolutionPrescription", evolutionPrescription.getId().toString()))
				.body(result);
	}

	/**
	 * GET /evolutionPrescriptions -> get all the evolutionPrescriptions.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/evolutionPrescriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<EvolutionPrescription>> getAllByEpisode(
			@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<EvolutionPrescription> page;

		if (episodeId != null && episodeId > 0) {
			page = evolutionPrescriptionRepository.findByEpisode(episodeId,
					PaginationUtil.generatePageRequest(offset, limit));
		} else {
			page = evolutionPrescriptionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes/"+episodeId+"/evolutionPrescriptions", offset,
				limit);
		return new ResponseEntity<List<EvolutionPrescription>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /evolutionPrescriptions/:id -> get the "id" evolutionPrescription.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/evolutionPrescriptions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EvolutionPrescription> getByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get EvolutionPrescription : {}", id);
		EvolutionPrescription evolutionPrescription = evolutionPrescriptionRepository.findOneByEpisode(episodeId, id);
		if (evolutionPrescription == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(evolutionPrescription, HttpStatus.OK);
	}

	/**
	 * DELETE /evolutionPrescriptions/:id -> delete the "id"
	 * evolutionPrescription.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/evolutionPrescriptions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete EvolutionPrescription : {}", id);
		evolutionPrescriptionRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evolutionPrescription", id.toString()))
				.build();
	}
}
