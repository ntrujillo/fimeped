package org.uce.fimeped.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.ClinicHistory;

/**
 * Spring Data JPA repository for the ClinicHistory entity.
 */
public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {

	
	@Query(value = "select c from ClinicHistory c where upper(isnull(c.person.firstName,'')) like upper(:firstName) "
			+ "and upper(isnull(c.person.secondName,'')) like upper(:secondName) "
			+ "and upper(isnull(c.person.paternalSurname,'')) like upper(:paternalSurname) "
			+ "and upper(isnull(c.person.maternalSurname,'')) like upper(:maternalSurname) "
			+ "and isnull(c.person.cedula,'') like :cedula", 
			countQuery = "select count(c) from ClinicHistory c where upper(isnull(c.person.firstName,'')) like upper(:firstName) "
					+ "and upper(isnull(c.person.secondName,'')) like upper(:secondName) "
					+ "and upper(isnull(c.person.paternalSurname,'')) like upper(:paternalSurname) "
					+ "and upper(isnull(c.person.maternalSurname,'')) like upper(:maternalSurname) "
					+ "and isnull(c.person.cedula,'') like :cedula")
	Page<ClinicHistory> findByFilter(@Param("firstName") String firstName, @Param("secondName") String secondName,
			@Param("paternalSurname") String paternalSurname, @Param("maternalSurname") String maternalSurname,
			@Param("cedula") String cedula, Pageable pageable);	
	
	@Query(value = "select c from ClinicHistory c where c.person.id = :personId")
	List<ClinicHistory> findByPerson(@Param("personId") long personId);

}
