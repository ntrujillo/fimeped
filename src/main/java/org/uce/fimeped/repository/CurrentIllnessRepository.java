package org.uce.fimeped.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.CurrentIllness;



/**
 * Spring Data JPA repository for the CurrentIllness entity.
 */
public interface CurrentIllnessRepository extends JpaRepository<CurrentIllness,Long> {
	
	@Query(value = "select r from CurrentIllness r where r.episode.id = :episodeId", 
			countQuery = "select count(r) from CurrentIllness r where r.episode.id = :episodeId")
	Page<CurrentIllness> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	@Query(value = "select r from CurrentIllness r where r.episode.id = :episodeId and r.id = :id")
	CurrentIllness findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);

	
}
