package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT * FROM cinema_room cr WHERE cr.name = ?1", nativeQuery = true)
    Optional<Room> getRoomByName(String name);

    @Modifying
    @Query(value = "DELETE  FROM cinema_room WHERE name = ?1", nativeQuery = true)
    void deleteByTitle(String name);
}
