package org.uce.fimeped.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import org.uce.fimeped.domain.Catalog;
import org.uce.fimeped.domain.CatalogId;
import org.uce.fimeped.domain.ICD10;
import org.uce.fimeped.domain.Tabla;
import org.uce.fimeped.repository.CatalogRepository;
import org.uce.fimeped.repository.ICD10Repository;
import org.uce.fimeped.web.rest.dto.CatalogDTO;
import org.uce.fimeped.web.rest.util.HeaderUtil;
import org.uce.fimeped.web.rest.util.PaginationUtil;
import org.uce.fimeped.web.rest.util.SortUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Catalog.
 */
@RestController
@RequestMapping("/api")
public class CatalogResource {

	private final Logger log = LoggerFactory.getLogger(CatalogResource.class);

	@Inject
	private CatalogRepository catalogRepository;

	@Inject
	private ICD10Repository icd10Repository;
	
	
	
	/**
     * POST  /tablas/tab_id/catalogs -> Create a new catalog.
     */
    @RequestMapping(value = "/tablas/{tab_id}/catalogs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogDTO> create(@PathVariable(value = "tab_id") String tabId, @Valid @RequestBody CatalogDTO catalogDTO) throws URISyntaxException {
        log.debug("REST request to save Catalog : {}", catalogDTO);
        if (catalogDTO.getCode() == null) {
            return ResponseEntity.badRequest().header("Failure", "A new catalog should have an ID").body(null);
        }
        CatalogId catalogId = new CatalogId(tabId,catalogDTO.getCode());
        Tabla tabla = new Tabla();
        tabla.setTabId(tabId);
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalogId);
        catalog.setTabla(tabla);     
        Catalog result = catalogRepository.save(catalog);
        return ResponseEntity.created(new URI("/api/tablas/"+tabId+"/catalogs/" + catalogDTO.getCode()))
                .headers(HeaderUtil.createEntityCreationAlert("catalog", catalogDTO.getCode()))
                .body(catalogDTO);
    }

    /**
     * PUT  /tablas/tab_id/catalogs -> Updates an existing catalog.
     */
    @RequestMapping(value = "tablas/{tab_id}/catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogDTO> update(@PathVariable(value = "tab_id") String tabId, @Valid @RequestBody CatalogDTO catalogDTO) throws URISyntaxException {
        log.debug("REST request to update Catalog : {}", catalogDTO);
        if (catalogDTO.getCode() == null) {
            return create(tabId,catalogDTO);
        }
        CatalogId catalogId = new CatalogId(tabId,catalogDTO.getCode());
        Tabla tabla = new Tabla();
        tabla.setTabId(tabId);
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalogId);
        catalog.setTabla(tabla);
        Catalog result = catalogRepository.save(catalog);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("catalog",catalogDTO.getCode()))
                .body(catalogDTO);
    }
	

	/**
	 * GET /tablas/:tab_id/catalogs/ -> get catalogs.
	 */
	@RequestMapping(value = "/tablas/{tab_id}/catalogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<CatalogDTO>> get(@PathVariable(value = "tab_id") String tabId,
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort) {
		log.debug("REST request to get Catalog : {}", tabId);
		List<CatalogDTO> catalog;
		Page<Catalog> items;
		HttpHeaders headers;
		try {
			items = catalogRepository.findByTabla(tabId, PaginationUtil.generatePageSortRequest(offset, limit,SortUtil.generateSortRequest(sort)));
			CatalogDTO tmp;
			catalog = new ArrayList<>();
			for (Catalog c : items) {
				tmp = new CatalogDTO();
				tmp.setCode(c.getCatalogId().getCatId());
				tmp.setValue(c.getValue());
				catalog.add(tmp);
			}
			headers = PaginationUtil.generatePaginationHttpHeaders(items, "/api/tablas/"+tabId+"/catalogs", offset, limit);
		} catch (Exception e) {
			log.debug("Exception", e);
			return ResponseEntity.badRequest().header("Failure", e.getMessage()).body(null);
		}

		return new ResponseEntity<List<CatalogDTO>>(catalog, headers, HttpStatus.OK);

	}
	

    /**
     * GET  /tablas/:tab_id/catalogs/:tabla/:id -> get the "id" catalog.
     */
    @RequestMapping(value = "/tablas/{tab_id}/catalogs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogDTO> get(@PathVariable String tab_id, @PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Tabla : {} Catalog : {} ",tab_id, id);
        CatalogId catalogId = new CatalogId();
        catalogId.setTabId(tab_id);
        catalogId.setCatId(id);        
        Catalog catalog = catalogRepository.findOne(catalogId);
        if (catalog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setCode(catalog.getCatalogId().getCatId());
        catalogDTO.setValue(catalog.getValue());
        return new ResponseEntity<>(catalogDTO, HttpStatus.OK);
    }
	
	
	 /**
     * DELETE  /tablas/:tab_id/catalogs/:id -> delete the "id" catalog.
     */
    @RequestMapping(value = "/tablas/{tab_id}/catalogs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String tab_id,@PathVariable String id) {
        log.debug("REST request to delete Tabla : {} Catalog {} ", tab_id, id);
        CatalogId catalogId = new CatalogId();
        catalogId.setTabId(tab_id);
        catalogId.setCatId(id);
        catalogRepository.delete(catalogId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("catalog", catalogId.toString())).build();
    }
	

	/**
	 * GET /icd10 -> get the "icd10" catalog.
	 */
	@RequestMapping(value = "/icd10", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ICD10>> getIcd10(@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "description", required = false) String description) throws URISyntaxException {
		log.debug("REST request to get all ClinicHistorys");
		code = code == null ? "%" : "%" + code + "%";
		description = description == null ? "%" : "%" + description + "%";
		Page<ICD10> page = icd10Repository.findByFilter(code, description,
				PaginationUtil.generatePageRequest(offset, limit));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/icd10", offset, limit);
		return new ResponseEntity<List<ICD10>>(page.getContent(), headers, HttpStatus.OK);

	}
}
