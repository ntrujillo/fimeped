package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.uce.fimeped.domain.Canton;
import org.uce.fimeped.repository.CantonRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Canton.
 */
@RestController
@RequestMapping("/api")
public class CantonResource {

    private final Logger log = LoggerFactory.getLogger(CantonResource.class);

    @Inject
    private CantonRepository cantonRepository;

    /**
     * POST  /cantons -> Create a new canton.
     */
    @RequestMapping(value = "/cantons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Canton> create(@Valid @RequestBody Canton canton) throws URISyntaxException {
        log.debug("REST request to save Canton : {}", canton);
        if (canton.getId() == null) {
            return ResponseEntity.badRequest().header("Failure", "A new canton should have an ID").body(null);
        }
        Canton result = cantonRepository.save(canton);
        return ResponseEntity.created(new URI("/api/cantons/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("canton", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /cantons -> Updates an existing canton.
     */
    @RequestMapping(value = "/cantons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Canton> update(@Valid @RequestBody Canton canton) throws URISyntaxException {
        log.debug("REST request to update Canton : {}", canton);
        if (canton.getId() == null) {
            return create(canton);
        }
        Canton result = cantonRepository.save(canton);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("canton", canton.getId().toString()))
                .body(result);
    }

    /**
     * GET  /cantons -> get all the cantons.
     */
    @RequestMapping(value = "/cantons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Canton>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	log.debug("Start getAll in "+this.getClass().toString());
    	log.debug("page "+offset);
    	log.debug("per_page "+limit);
    	log.debug("Start getAll in "+this.getClass().toString());
        Page<Canton> page = cantonRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cantons", offset, limit);
        log.debug("End getAll in "+this.getClass().toString());
        return new ResponseEntity<List<Canton>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cantons/:id -> get the "id" canton.
     */
    @RequestMapping(value = "/cantons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Canton> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Canton : {}", id);
        Canton canton = cantonRepository.findOne(id);
        if (canton == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(canton, HttpStatus.OK);
    }

    /**
     * DELETE  /cantons/:id -> delete the "id" canton.
     */
    @RequestMapping(value = "/cantons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Canton : {}", id);
        cantonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("canton", id.toString())).build();
    }
}
