package org.uce.fimeped.repository;

import org.uce.fimeped.domain.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Plan entity.
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {

	@Query(value = "select r from Plan r where r.episode.id = :episodeId", countQuery = "select count(r) from Plan r where r.episode.id = :episodeId")
	Page<Plan> findByEpisode(@Param("episodeId") Long episodeId, Pageable pageable);

	@Query(value = "select r from Plan r where r.episode.id = :episodeId and r.id = :id")
	Plan findOneByEpisode(@Param("episodeId") Long episodeId, @Param("id") Long id);

}
