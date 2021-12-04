package com.epam.training.ticketservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    @Query(value = "SELECT * FROM cinema_screening cs WHERE cs.room_id = ?1 AND cs.screening_date BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Screening> findScreeningsInRoomOnDay(int roomId, LocalDateTime start, LocalDateTime end);

}
