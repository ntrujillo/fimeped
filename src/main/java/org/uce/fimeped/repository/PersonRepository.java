package org.uce.fimeped.repository;


import org.uce.fimeped.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Person entity.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query(value = "select p from Person p where upper(isnull(p.firstName,'')) like upper(:firstName) "
			+ "and upper(isnull(p.secondName,'')) like upper(:secondName) "
			+ "and upper(isnull(p.paternalSurname,'')) like upper(:paternalSurname) "
			+ "and upper(isnull(p.maternalSurname,'')) like upper(:maternalSurname) "
			+ "and isnull(p.cedula,'') like :cedula", 
			countQuery = "select count(p) from Person p where upper(isnull(p.firstName,'')) like upper(:firstName) "
			+ "and upper(isnull(p.secondName,'')) like upper(:secondName) "
			+ "and upper(isnull(p.paternalSurname,'')) like upper(:paternalSurname) "
			+ "and upper(isnull(p.maternalSurname,'')) like upper(:maternalSurname) "
			+ "and isnull(p.cedula,'') like :cedula")
	Page<Person> findByFilter(@Param("firstName") String firstName, @Param("secondName") String secondName,
			@Param("paternalSurname") String paternalSurname, @Param("maternalSurname") String maternalSurname,
			@Param("cedula") String cedula, Pageable pageable);
}
