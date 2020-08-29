package org.uce.fimeped.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.Catalog;
import org.uce.fimeped.domain.CatalogId;

public interface CatalogRepository extends JpaRepository<Catalog, CatalogId> {

	@Query(value = "select c from Catalog c where upper(c.tabla.tabId) like upper(:tabla)", 
			countQuery = "select count(c) from Catalog c where upper(c.tabla.tabId) like upper(:tabla)")
	Page<Catalog> findByTabla(@Param("tabla") String tabla, Pageable pageable);

}
