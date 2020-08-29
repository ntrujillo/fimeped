package org.uce.fimeped.repository;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.uce.fimeped.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query(value = "select r from Reservation r where r.date >= :date", 
			countQuery = "select count(r) from Reservation r where r.date >= :date")
	Page<Reservation> findByDate(@Param("date") LocalDate date, Pageable pageable);

}
