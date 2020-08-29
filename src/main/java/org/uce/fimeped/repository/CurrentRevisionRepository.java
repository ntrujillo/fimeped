package org.uce.fimeped.repository;



import org.uce.fimeped.domain.CurrentRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;



/**
 * Spring Data JPA repository for the CurrentRevision entity.
 */
public interface CurrentRevisionRepository extends JpaRepository<CurrentRevision, Long> {
	@Query(value = "select r from CurrentRevision r where r.episode.id = :episodeId", 
			countQuery = "select count(r) from CurrentRevision r where r.episode.id = :episodeId")
	Page<CurrentRevision> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	@Query(value = "select r from CurrentRevision r where r.episode.id = :episodeId and r.id = :id")
	CurrentRevision findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);
}
