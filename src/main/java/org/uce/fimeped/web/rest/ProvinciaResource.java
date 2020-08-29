package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.uce.fimeped.domain.Provincia;
import org.uce.fimeped.repository.ProvinciaRepository;
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
 * REST controller for managing Provincia.
 */
@RestController
@RequestMapping("/api")
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);

    @Inject
    private ProvinciaRepository provinciaRepository;

    /**
     * POST  /provincias -> Create a new provincia.
     */
    @RequestMapping(value = "/provincias",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provincia> create(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provincia);
        if (provincia.getId() == null) {
            return ResponseEntity.badRequest().header("Failure", "A new provincia should have an id").body(null);
        }
        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.created(new URI("/api/provincias/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("provincia", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /provincias -> Updates an existing provincia.
     */
    @RequestMapping(value = "/provincias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provincia> update(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}", provincia);
        if (provincia.getId() == null) {
            return create(provincia);
        }
        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("provincia", provincia.getId().toString()))
                .body(result);
    }

    /**
     * GET  /provincias -> get all the provincias.
     */
    @RequestMapping(value = "/provincias",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Provincia>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Provincia> page = provinciaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provincias", offset, limit);
        return new ResponseEntity<List<Provincia>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provincias/:id -> get the "id" provincia.
     */
    @RequestMapping(value = "/provincias/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provincia> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Provincia : {}", id);
        Provincia provincia = provinciaRepository.findOne(id);
        if (provincia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provincia, HttpStatus.OK);
    }

    /**
     * DELETE  /provincias/:id -> delete the "id" provincia.
     */
    @RequestMapping(value = "/provincias/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("provincia", id.toString())).build();
    }
}
