package org.uce.fimeped.repository;


import org.uce.fimeped.domain.Diagnostic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;



/**
 * Spring Data JPA repository for the Diagnostic entity.
 */
public interface DiagnosticRepository extends JpaRepository<Diagnostic,Long> {
	
	@Query(value = "select d from Diagnostic d where d.episode.id = :episodeId", 
			countQuery = "select count(d) from Diagnostic d where d.episode.id = :episodeId")
	Page<Diagnostic> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	
	@Query(value = "select d from Diagnostic d where d.episode.id = :episodeId and d.id = :id")
	Diagnostic findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);
}
