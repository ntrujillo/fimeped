package org.uce.fimeped.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.uce.fimeped.domain.ClinicHistoryAddInf;
import org.uce.fimeped.repository.ClinicHistoryAddInfRepository;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing ClinicHistoryAddInf.
 */
@RestController
@RequestMapping("/api")
public class ClinicHistoryAddInfResource {

    private final Logger log = LoggerFactory.getLogger(ClinicHistoryAddInfResource.class);

    @Inject
    private ClinicHistoryAddInfRepository clinicHistoryAddInfRepository;

    /**
     * POST  /clinicHistoryAddInfs -> Create a new clinicHistoryAddInf.
     */
    @RequestMapping(value = "/clinicHistoryAddInfs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicHistoryAddInf> create(@RequestBody ClinicHistoryAddInf clinicHistoryAddInf) throws URISyntaxException {
        log.debug("REST request to save ClinicHistoryAddInf : {}", clinicHistoryAddInf);
        if (clinicHistoryAddInf.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new clinicHistoryAddInf cannot already have an ID").body(null);
        }
        ClinicHistoryAddInf result = clinicHistoryAddInfRepository.save(clinicHistoryAddInf);
        return ResponseEntity.created(new URI("/api/clinicHistoryAddInfs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("clinicHistoryAddInf", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /clinicHistoryAddInfs -> Updates an existing clinicHistoryAddInf.
     */
    @RequestMapping(value = "/clinicHistoryAddInfs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicHistoryAddInf> update(@RequestBody ClinicHistoryAddInf clinicHistoryAddInf) throws URISyntaxException {
        log.debug("REST request to update ClinicHistoryAddInf : {}", clinicHistoryAddInf);
        if (clinicHistoryAddInf.getId() == null) {
            return create(clinicHistoryAddInf);
        }
        ClinicHistoryAddInf result = clinicHistoryAddInfRepository.save(clinicHistoryAddInf);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("clinicHistoryAddInf", clinicHistoryAddInf.getId().toString()))
                .body(result);
    }

    /**
     * GET  /clinicHistoryAddInfs -> get all the clinicHistoryAddInfs.
     */
    @RequestMapping(value = "/clinicHistoryAddInfs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClinicHistoryAddInf> getAll(@RequestParam(required = false) String filter) {
        if ("clinic_add_inf-is-null".equals(filter)) {
            log.debug("REST request to get all ClinicHistoryAddInfs where clinic_add_inf is null");
            List clinicHistoryAddInfs = new ArrayList<ClinicHistoryAddInf>();
            for (ClinicHistoryAddInf clinicHistoryAddInf : clinicHistoryAddInfRepository.findAll()) {
                if (clinicHistoryAddInf.getClinicHistory()== null) {
                    clinicHistoryAddInfs.add(clinicHistoryAddInf);
                }
            }
            return clinicHistoryAddInfs;
        }

        log.debug("REST request to get all ClinicHistoryAddInfs");
        return clinicHistoryAddInfRepository.findAll();
    }

    /**
     * GET  /clinicHistoryAddInfs/:id -> get the "id" clinicHistoryAddInf.
     */
    @RequestMapping(value = "/clinicHistoryAddInfs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicHistoryAddInf> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ClinicHistoryAddInf : {}", id);
        ClinicHistoryAddInf clinicHistoryAddInf = clinicHistoryAddInfRepository.findOne(id);
        if (clinicHistoryAddInf == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clinicHistoryAddInf, HttpStatus.OK);
    }

    /**
     * DELETE  /clinicHistoryAddInfs/:id -> delete the "id" clinicHistoryAddInf.
     */
    @RequestMapping(value = "/clinicHistoryAddInfs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ClinicHistoryAddInf : {}", id);
        clinicHistoryAddInfRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clinicHistoryAddInf", id.toString())).build();
    }
}
