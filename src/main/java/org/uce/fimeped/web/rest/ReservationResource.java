package org.uce.fimeped.web.rest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uce.fimeped.domain.Reservation;
import org.uce.fimeped.repository.ReservationRepository;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class ReservationResource {
	
	private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

	@Inject
	private ReservationRepository reservationRepository;
	
	/**
	 * GET /reservations -> get all the reservations.
	 */
	@RequestMapping(value = "/reservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Reservation>> getAll(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "date", required = false) String date) throws URISyntaxException {
		
		LocalDate startDate = null;
		if (date != null && !date.equals("")) {
			log.debug(date);
			startDate = new LocalDate(date);
		}
		

		Page<Reservation> page;		
		page = reservationRepository.findByDate(startDate, PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservations", offset, limit);
		return new ResponseEntity<List<Reservation>>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /reservations/:id -> get the "id" reservations.
	 */
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Reservation> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get reservation : {}", id);
		Reservation reservation = reservationRepository.findOne(id);
		if (reservation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

}
