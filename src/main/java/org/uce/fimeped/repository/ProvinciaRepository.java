package org.uce.fimeped.repository;

import org.uce.fimeped.domain.Provincia;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Provincia entity.
 */
public interface ProvinciaRepository extends JpaRepository<Provincia,String> {

}
