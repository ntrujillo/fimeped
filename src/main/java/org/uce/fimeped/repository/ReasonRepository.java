package org.uce.fimeped.repository;

import org.uce.fimeped.domain.Reason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Reason entity.
 */
public interface ReasonRepository extends JpaRepository<Reason, Long> {


	@Query(value = "select r from Reason r where r.episode.id = :episodeId", 
			countQuery = "select count(r) from Reason r where r.episode.id = :episodeId")
	Page<Reason> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	@Query(value = "select r from Reason r where r.episode.id = :episodeId and r.id = :id")
	Reason findOneByEpisode(@Param("episodeId") Long episodeId,@Param("id") Long id);


}
