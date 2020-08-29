package org.uce.fimeped.repository;

import org.uce.fimeped.domain.PersonalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the PersonalHistory entity.
 */
public interface PersonalHistoryRepository extends JpaRepository<PersonalHistory,Long> {
	
	@Query(value = "select p from PersonalHistory p where p.clinicHistory.id = :historyId", 
			countQuery = "select count(p) from PersonalHistory p where p.clinicHistory.id = :historyId")
	Page<PersonalHistory> findByClinicHistory(@Param("historyId") Long historyId,Pageable pageable);
	
	@Query(value = "select p from PersonalHistory p where p.clinicHistory.id = :historyId and p.id = :id")
	PersonalHistory findOneByClinicHistory(@Param("historyId") Long historyId,@Param("id") Long id);

}
