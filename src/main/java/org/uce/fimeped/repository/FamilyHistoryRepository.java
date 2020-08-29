package org.uce.fimeped.repository;


import org.uce.fimeped.domain.FamilyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the FamilyHistory entity.
 */
public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory,Long> {

	
	@Query(value = "select f from FamilyHistory f where f.clinicHistory.id = :historyId", 
			countQuery = "select count(f) from FamilyHistory f where f.clinicHistory.id = :historyId")
	Page<FamilyHistory> findByClinicHistory(@Param("historyId") Long historyId,Pageable pageable);
	
	@Query(value = "select f from FamilyHistory f where f.clinicHistory.id = :historyId and f.id = :id")
	FamilyHistory findOneByClinicHistory(@Param("historyId") Long historyId,@Param("id") Long id);
}
