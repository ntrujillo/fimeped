package org.uce.fimeped.repository;



import org.uce.fimeped.domain.EvolutionPrescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the EvolutionPrescription entity.
 */
public interface EvolutionPrescriptionRepository extends JpaRepository<EvolutionPrescription,Long> {

	@Query(value = "select d from EvolutionPrescription d where d.episode.id = :episodeId", 
			countQuery = "select count(d) from EvolutionPrescription d where d.episode.id = :episodeId")
	Page<EvolutionPrescription> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	
	@Query(value = "select d from EvolutionPrescription d where d.episode.id = :episodeId and d.id = :id")
	EvolutionPrescription findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);
}
