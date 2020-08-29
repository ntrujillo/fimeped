package org.uce.fimeped.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
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
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.repository.EpisodeRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Episode.
 */
@RestController
@RequestMapping("/api")
public class EpisodeResource {

	private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

	@Inject
	private EpisodeRepository episodeRepository;

	/**
	 * POST /episodes -> Create a new episode.
	 */
	@RequestMapping(value = "/episodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> create(@RequestBody Episode episode) throws URISyntaxException {
		episode.setDate(new LocalDate());
		log.debug("REST request to save Episode : {}", episode);
		if (episode.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new episode cannot already have an ID").body(null);
		}
		Episode result = episodeRepository.save(episode);
		return ResponseEntity.created(new URI("/api/episodes/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("episode", result.getId().toString())).body(result);
	}

	/**
	 * PUT /episodes -> Updates an existing episode.
	 */
	@RequestMapping(value = "/episodes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> update(@RequestBody Episode episode) throws URISyntaxException {
		log.debug("REST request to update Episode : {}", episode);
		if (episode.getId() == null) {
			return create(episode);
		}
		Episode result = episodeRepository.save(episode);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("episode", episode.getId().toString()))
				.body(result);
	}

	/**
	 * GET /episodes -> get all the episodes.
	 */

	@RequestMapping(value = "/episodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Episode>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,			
			@RequestParam(value = "date_start", required = false) String startDate,
			@RequestParam(value = "date_end", required = false) String endDate) throws URISyntaxException {

		LocalDate start = null, end = null;
		if (startDate != null && !startDate.equals("")) {
			log.debug(startDate);
			start = new LocalDate(startDate);
		}
		if (endDate != null && !endDate.equals("")) {
			end = new LocalDate(endDate);
		}

		Page<Episode> page;

		if (start != null && end != null) {
			log.debug("Recuperando episodios con findByDateBetween");
			page = episodeRepository.findByDateBetween(start, end,
					PaginationUtil.generatePageRequest(offset, limit));
		} else if (start == null && end != null) {
			log.debug("Recuperando episodios con findByDateLessThan");
			page = episodeRepository.findByDateLess( end,
					PaginationUtil.generatePageRequest(offset, limit));
		} else if (start != null && end == null) {
			log.debug("Recuperando episodios con findByDateGreaterThan");
			page = episodeRepository.findByDateGreater( start,
					PaginationUtil.generatePageRequest(offset, limit));
		} else {
			log.debug("Recuperando episodios con findAll");
			page = episodeRepository.findAll( PaginationUtil.generatePageRequest(offset, limit));
		}

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/episodes", offset, limit);
		return new ResponseEntity<List<Episode>>(page.getContent(), headers, HttpStatus.OK);

	}
	
	/**
	 * GET /episodes/:id -> get the "id" episode.
	 */
	@RequestMapping(value = "/episodes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Episode : {}", id);
		Episode episode = episodeRepository.findOne(id);
		if (episode == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(episode, HttpStatus.OK);
	}
	
	

	/**
	 * DELETE /episodes/:id -> delete the "id" episode.
	 */
	@RequestMapping(value = "/episodes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete Episode : {}", id);
		episodeRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("episode", id.toString())).build();
	}
	
	
	/**By clinicHistorys**/
	
	/**
	 * POST /clinicHistorys/:history_id/episodes -> Create a new episode by clinicHistory.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/episodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> createByClinicHistory(@PathVariable(value = "history_id") Long historyId, @RequestBody Episode episode) throws URISyntaxException {
		episode.setDate(new LocalDate());
		log.debug("REST request to save Episode by clinicHistory: {} episode {}", historyId, episode);
		if (episode.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new episode cannot already have an ID").body(null);
		}
		
		if (historyId == null) {
			return ResponseEntity.badRequest().header("Failure", "A new episode should have a clinicHistory id").body(null);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		episode.setClinicHistory(clinicHistory);
		episode.setDate(new LocalDate());
		Episode result = episodeRepository.save(episode);
		return ResponseEntity.created(new URI("/api/clinicHistorys/"+historyId+"/episodes/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("episode", result.getId().toString())).body(result);
	}

	/**
	 * PUT /clinicHistorys/:history_id/episodes -> Updates an existing episode.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/episodes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> updateByClinicHistory(@PathVariable(value = "history_id") Long historyId, @RequestBody Episode episode) throws URISyntaxException {
		log.debug("REST request to update Episode by clinicHistory: {} episode {}", historyId, episode);
		if (episode.getId() == null) {
			return create(episode);
		}
		ClinicHistory clinicHistory = new ClinicHistory();
		clinicHistory.setId(historyId);
		episode.setClinicHistory(clinicHistory);
		Episode result = episodeRepository.save(episode);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("episode", episode.getId().toString()))
				.body(result);
	}
	
	
	
	
	/**
	 * GET /clinicHistorys/:history_id/episodes -> get all the episodes by clinicHistory.
	 */

	@RequestMapping(value = "/clinicHistorys/{history_id}/episodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Episode>> getAllByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,	
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "date_start", required = false) String startDate,
			@RequestParam(value = "date_end", required = false) String endDate) throws URISyntaxException {
		log.debug("REST request to get Episode by clinicHistory: {}", historyId);
		LocalDate start = null, end = null;
		if (startDate != null && !startDate.equals("")) {
			log.debug(startDate);
			start = new LocalDate(startDate);
		}
		if (endDate != null && !endDate.equals("")) {
			end = new LocalDate(endDate);
		}

		Page<Episode> page;

		if (start != null && end != null) {
			log.debug("Recuperando episodios con findByClinicHistoryAndDateLess");
			page = episodeRepository.findByClinicHistoryAndDateBetween(historyId, start, end,
					PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
		} else if (start == null && end != null) {
			log.debug("Recuperando episodios con findByClinicHistoryAndDateLess");
			page = episodeRepository.findByClinicHistoryAndDateLess(historyId, end,
					PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
		} else if (start != null && end == null) {
			log.debug("Recuperando episodios con findByClinicHistoryAndDateGreater");
			page = episodeRepository.findByClinicHistoryAndDateGreater(historyId, start,
					PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
		} else {
			log.debug("Recuperando episodios con findByClinicHistory");
			page = episodeRepository.findByClinicHistory(historyId, PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
		}

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinicHistorys/"+historyId+"/episodes", offset, limit);
		return new ResponseEntity<List<Episode>>(page.getContent(), headers, HttpStatus.OK);

	}

	/**
	 * GET /clinicHistorys/:history_id/episodes/:id -> get episode by clinicHistory and episode id.
	 */

	@RequestMapping(value = "/clinicHistorys/{history_id}/episodes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Episode> getByClinicHistory(
			@PathVariable(value = "history_id") Long historyId,
			@PathVariable(value = "id") Long id,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit
			) throws URISyntaxException {

		log.debug("REST request to get Episode by clinicHistory: {} episode: {}",historyId, id);
		Episode episode = episodeRepository.findOneByClinicHistory(historyId, id);
		if (episode == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(episode, HttpStatus.OK);

	}
	
	
	/**
	 * DELETE /clinicHistorys/:history_id/episodes/:id -> delete the "id" episode.
	 */
	@RequestMapping(value = "/clinicHistorys/{history_id}/episodes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteByClinicHistory(@PathVariable(value = "history_id") Long historyId, @PathVariable Long id) {
		log.debug("REST request to delete Episode by clinicHistory: {} episode: {}",historyId, id);
		episodeRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("episode", id.toString())).build();
	}

}
