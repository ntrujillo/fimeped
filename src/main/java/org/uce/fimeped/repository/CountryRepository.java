package org.uce.fimeped.repository;

import org.uce.fimeped.domain.Country;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Country entity.
 */
public interface CountryRepository extends JpaRepository<Country,String> {

}
