package org.uce.fimeped.repository;


import org.uce.fimeped.domain.PhysicalExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the PhysicalExam entity.
 */
public interface PhysicalExamRepository extends JpaRepository<PhysicalExam, Long> {
	@Query(value = "select r from PhysicalExam r where r.episode.id = :episodeId", 
			countQuery = "select count(r) from PhysicalExam r where r.episode.id = :episodeId")
	Page<PhysicalExam> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);
	
	@Query(value = "select r from PhysicalExam r where r.episode.id = :episodeId and r.id = :id")
	PhysicalExam findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);

}
