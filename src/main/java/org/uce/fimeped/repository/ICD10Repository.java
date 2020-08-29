package org.uce.fimeped.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.ICD10;

public interface ICD10Repository extends JpaRepository<ICD10, String> {	
	
	@Query(value = "select c from ICD10 c where code like :code "
			+ "and lower(isnull(c.description,'')) like lower(:description)", 
			countQuery = "select count(c) from ICD10 c where c.code like :code "
			+ "and lower(isnull(c.description,'')) like lower(:description)")
	Page<ICD10> findByFilter(@Param("code")  String code, @Param("description")  String description, Pageable pageable);

	
}
