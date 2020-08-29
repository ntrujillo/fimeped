package org.uce.fimeped.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.uce.fimeped.domain.Tabla;
import org.uce.fimeped.repository.TablaRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Tabla.
 */
@RestController
@RequestMapping("/api")
public class TablaResource {
	
	   private final Logger log = LoggerFactory.getLogger(TablaResource.class);

	    @Inject
	    private TablaRepository tablaRepository;

	    /**
	     * POST  /tablas -> Create a new tabla.
	     */
	    @RequestMapping(value = "/tablas",
	            method = RequestMethod.POST,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<Tabla> create(@Valid @RequestBody Tabla tabla) throws URISyntaxException {
	        log.debug("REST request to save Tabla : {}", tabla);
	        if (tabla.getTabId() == null) {
	            return ResponseEntity.badRequest().header("Failure", "A new tabla should have an ID").body(null);
	        }
	        Tabla result = tablaRepository.save(tabla);
	        return ResponseEntity.created(new URI("/api/tablas/" + result.getTabId()))
	                .headers(HeaderUtil.createEntityCreationAlert("tabla", result.getTabId()))
	                .body(result);
	    }

	    /**
	     * PUT  /tablas -> Updates an existing tabla.
	     */
	    @RequestMapping(value = "/tablas",
	        method = RequestMethod.PUT,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<Tabla> update(@Valid @RequestBody Tabla tabla) throws URISyntaxException {
	        log.debug("REST request to update Tabla : {}", tabla);
	        if (tabla.getTabId() == null) {
	            return create(tabla);
	        }
	        Tabla result = tablaRepository.save(tabla);
	        return ResponseEntity.ok()
	                .headers(HeaderUtil.createEntityUpdateAlert("tabla",tabla.getTabId()))
	                .body(result);
	    }

	    /**
	     * GET  /tablas -> get all the tablas.
	     */
	    @RequestMapping(value = "/tablas",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<List<Tabla>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
	                                  @RequestParam(value = "per_page", required = false) Integer limit,
	                                  @RequestParam(value = "sort", required = false) String sort)
	        throws URISyntaxException {
	        Page<Tabla> page = tablaRepository.findAll(PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tablas", offset, limit);
	        return new ResponseEntity<List<Tabla>>(page.getContent(), headers, HttpStatus.OK);
	    }

	    /**
	     * GET  /tablas/:id -> get the "id" tabla.
	     */
	    @RequestMapping(value = "/tablas/{id}",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<Tabla> get(@PathVariable String id, HttpServletResponse response) {
	        log.debug("REST request to get Tabla : {}", id);
	        Tabla tabla = tablaRepository.findOne(id);
	        if (tabla == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(tabla, HttpStatus.OK);
	    }

	    /**
	     * DELETE  /tablas/:id -> delete the "id" tabla.
	     */
	    @RequestMapping(value = "/tablas/{id}",
	            method = RequestMethod.DELETE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<Void> delete(@PathVariable String id) {
	        log.debug("REST request to delete Tabla : {}", id);
	        tablaRepository.delete(id);
	        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tabla", id.toString())).build();
	    }

}
