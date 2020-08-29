package org.uce.fimeped.repository;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.Episode;

/**
 * Spring Data JPA repository for the Episode entity.
 */
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
	
	Page<Episode> findByDateBetween( LocalDate startDate, LocalDate endDate, Pageable pageable);
	
	@Query(value = "select e from Episode e where e.date >= :date", 
			countQuery = "select count(e) from Episode e where e.date >= :date")
	Page<Episode> findByDateGreater(@Param("date") LocalDate date, Pageable pageable);
	
	@Query(value = "select e from Episode e where e.date <= :date", 
			countQuery = "select count(e) from Episode e where e.date <= :date")
	Page<Episode> findByDateLess(@Param("date") LocalDate date, Pageable pageable);
	
	
	/**Episode by clinicHistory**/
	
	@Query(value = "select e from Episode e where e.clinicHistory.id = :historyId and e.id = :id")
	Episode findOneByClinicHistory(@Param("historyId") Long historyId,@Param("id") Long id);

	@Query(value = "select e from Episode e where e.clinicHistory.id = :historyId", 
			countQuery = "select count(e) from Episode e where e.clinicHistory.id = :historyId")
	Page<Episode> findByClinicHistory(@Param("historyId") Long historyId,Pageable pageable);
	
	@Query(value = "select e from Episode e where e.clinicHistory.id = :historyId and e.date between :startDate and :endDate", 
			countQuery = "select count(e) from Episode e where e.clinicHistory.id = :historyId and e.date between :startDate and :endDate")
	Page<Episode> findByClinicHistoryAndDateBetween(@Param("historyId") Long historyId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

	@Query(value = "select e from Episode e where e.clinicHistory.id =  :historyId and e.date <= :date", 
			countQuery = "select count(e) from Episode e where e.clinicHistory.id =  :historyId and e.date <= :date")
	Page<Episode> findByClinicHistoryAndDateLess(@Param("historyId") Long historyId,@Param("date") LocalDate date, Pageable pageable);

	@Query(value = "select e from Episode e where e.clinicHistory.id = :historyId and e.date >= :date", 
			countQuery = "select count(e) from Episode e where e.clinicHistory.id = :historyId and e.date >= :date")
	Page<Episode> findByClinicHistoryAndDateGreater(@Param("historyId") Long historyId,@Param("date") LocalDate date, Pageable pageable);
	
	
	
	

}
