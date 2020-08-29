package org.uce.fimeped.repository;

import org.uce.fimeped.domain.Canton;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Canton entity.
 */
public interface CantonRepository extends JpaRepository<Canton,String> {

}
