package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.Plan;
import org.uce.fimeped.repository.PlanRepository;
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
 * REST controller for managing Plan.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {

	private final Logger log = LoggerFactory.getLogger(PlanResource.class);

	@Inject
	private PlanRepository planRepository;

	/**
	 * POST /plans -> Create a new plan.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> create(@RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to save Plan : {}", plan);
		if (plan.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new plan cannot already have an ID").body(null);
		}
		Plan result = planRepository.save(plan);
		return ResponseEntity.created(new URI("/api/plans/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("plan", result.getId().toString())).body(result);
	}

	/**
	 * PUT /plans -> Updates an existing plan.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> update(@RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to update Plan : {}", plan);
		if (plan.getId() == null) {
			return create(plan);
		}
		Plan result = planRepository.save(plan);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("plan", plan.getId().toString()))
				.body(result);
	}

	/**
	 * GET /plans -> get all the plans.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Plan>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<Plan> page;
		page = planRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plans", offset, limit);
		return new ResponseEntity<List<Plan>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /plans/:id -> get the "id" plan.
	 */
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Plan : {}", id);
		Plan plan = planRepository.findOne(id);
		if (plan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(plan, HttpStatus.OK);
	}

	/**
	 * DELETE /plans/:id -> delete the "id" plan.
	 */
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete Plan : {}", id);
		planRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plan", id.toString())).build();
	}

	/** Plan by Espisode **/

	/**
	 * POST /plans -> Create a new plan.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/plans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> createByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to save Plan : {}", plan);
		if (plan.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new plan cannot already have an ID").body(null);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		plan.setEpisode(episode);
		Plan result = planRepository.save(plan);
		return ResponseEntity.created(new URI("/api/episodes/"+episodeId+"/plans/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("plan", result.getId().toString())).body(result);
	}

	/**
	 * PUT /plans -> Updates an existing plan.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/plans", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> updateByEpisode(@PathVariable(value = "episode_id") Long episodeId, @RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to update Plan : {}", plan);
		if (plan.getId() == null) {
			return create(plan);
		}
		Episode episode = new Episode();
		episode.setId(episodeId);
		plan.setEpisode(episode);
		Plan result = planRepository.save(plan);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("plan", plan.getId().toString()))
				.body(result);
	}

	/**
	 * GET /plans -> get all the plans.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/plans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Plan>> getAllByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
		Page<Plan> page;	
		page = planRepository.findByEpisode(episodeId, PaginationUtil.generatePageRequest(offset, limit));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes/"+episodeId+"/plans", offset, limit);
		return new ResponseEntity<List<Plan>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /plans/:id -> get the "id" plan.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/plans/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> getByEpisode(@PathVariable(value = "episode_id") Long episodeId, @PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get Plan : {}", id);
		Plan plan = planRepository.findOneByEpisode(episodeId, id);
		if (plan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(plan, HttpStatus.OK);
	}

	/**
	 * DELETE /plans/:id -> delete the "id" plan.
	 */
	@RequestMapping(value = "/episodes/{episode_id}/plans/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByEpisode(@PathVariable(value = "episode_id") Long episodeId,
			@PathVariable Long id) {
		log.debug("REST request to delete Plan : {}", id);
		planRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plan", id.toString())).build();
	}
}
