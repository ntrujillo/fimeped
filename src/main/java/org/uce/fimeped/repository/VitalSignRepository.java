package org.uce.fimeped.repository;


import org.uce.fimeped.domain.VitalSign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the VitalSign entity.
 */
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

	
	@Query(value = "select v from VitalSign v where v.clinicHistory.id = :historyId", 
			countQuery = "select count(v) from VitalSign v where v.clinicHistory.id = :historyId")
	Page<VitalSign> findByClinicHistory(@Param("historyId") Long historyId, Pageable pageable);
	
	
	@Query(value = "select v from VitalSign v where v.clinicHistory.id = :historyId and v.id = :id", 
			countQuery = "select count(v) from VitalSign v where v.clinicHistory.id = :historyId and v.id = :id")
	VitalSign findOneByClinicHistory(@Param("historyId") Long historyId,@Param("id") Long id);
	

}
