package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.PhysicalExam;
import org.uce.fimeped.repository.PhysicalExamRepository;
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
 * REST controller for managing PhysicalExam.
 */
@RestController
@RequestMapping("/api")
public class PhysicalExamResource {

	private final Logger log = LoggerFactory.getLogger(PhysicalExamResource.class);

	@Inject
	private PhysicalExamRepository physicalExamRepository;

	/**
	 * POST /physicalExams -> Create a new physicalExam.
	 */
	@RequestMapping(value = "/physicalExams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> create(@RequestBody PhysicalExam physicalExam) throws URISyntaxException {
		log.debug("REST request to save PhysicalExam : {}", physicalExam);
		if (physicalExam.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new physicalExam cannot already have an ID")
					.body(null);
		}
		PhysicalExam result = physicalExamRepository.save(physicalExam);
		return ResponseEntity.created(new URI("/api/physicalExams/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("physicalExam", result.getId().toString())).body(result);
	}

	/**
	 * PUT /physicalExams -> Updates an existing physicalExam.
	 */
	@RequestMapping(value = "/physicalExams", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> update(@RequestBody PhysicalExam physicalExam) throws URISyntaxException {
		log.debug("REST request to update PhysicalExam : {}", physicalExam);
		if (physicalExam.getId() == null) {
			return create(physicalExam);
		}
		PhysicalExam result = physicalExamRepository.save(physicalExam);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("physicalExam", physicalExam.getId().toString()))
				.body(result);
	}

	/**
	 * GET /physicalExams -> get all the physicalExams.
	 */
	@RequestMapping(value = "/physicalExams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<PhysicalExam>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<PhysicalExam> page;
		page = physicalExamRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/physicalExams", offset, limit);
		return new ResponseEntity<List<PhysicalExam>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /physicalExams/:id -> get the "id" physicalExam.
	 */
	@RequestMapping(value = "/physicalExams/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get PhysicalExam : {}", id);
		PhysicalExam physicalExam = physicalExamRepository.findOne(id);
		if (physicalExam == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(physicalExam, HttpStatus.OK);
	}

	/**
	 * DELETE /physicalExams/:id -> delete the "id" physicalExam.
	 */
	@RequestMapping(value = "/physicalExams/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete PhysicalExam : {}", id);
		physicalExamRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("physicalExam", id.toString())).build();
	}

	/** PhysicalExam by Episode **/

	/**
	 * POST /physicalExams -> Create a new physicalExam.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/physicalExams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> createByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody PhysicalExam physicalExam) throws URISyntaxException {
		log.debug("REST request to save PhysicalExam : {}", physicalExam);
		if (physicalExam.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new physicalExam cannot already have an ID")
					.body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		physicalExam.setEpisode(episode);
		PhysicalExam result = physicalExamRepository.save(physicalExam);
		return ResponseEntity.created(new URI("/api/episodes/" + episodeId + "/physicalExams/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("physicalExam", result.getId().toString())).body(result);
	}

	/**
	 * PUT /physicalExams -> Updates an existing physicalExam.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/physicalExams", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestBody PhysicalExam physicalExam) throws URISyntaxException {
		log.debug("REST request to update PhysicalExam : {}", physicalExam);
		if (physicalExam.getId() == null) {
			return create(physicalExam);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		physicalExam.setEpisode(episode);
		PhysicalExam result = physicalExamRepository.save(physicalExam);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("physicalExam", physicalExam.getId().toString()))
				.body(result);
	}

	/**
	 * GET /physicalExams -> get all the physicalExams.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/physicalExams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<PhysicalExam>> getAllByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<PhysicalExam> page;
		page = physicalExamRepository.findByEpisode(episodeId, PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/episodes/" + episodeId + "/physicalExams", offset, limit);
		return new ResponseEntity<List<PhysicalExam>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /physicalExams/:id -> get the "id" physicalExam.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/physicalExams/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PhysicalExam> getByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get PhysicalExam : {}", id);
		PhysicalExam physicalExam = physicalExamRepository.findOneByEpisode(episodeId, id);
		if (physicalExam == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(physicalExam, HttpStatus.OK);
	}

	/**
	 * DELETE /physicalExams/:id -> delete the "id" physicalExam.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/physicalExams/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete PhysicalExam : {}", id);
		physicalExamRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("physicalExam", id.toString())).build();
	}
}
