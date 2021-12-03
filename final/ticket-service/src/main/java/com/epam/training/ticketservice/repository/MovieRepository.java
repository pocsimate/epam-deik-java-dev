package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM movie m WHERE m.title = ?1", nativeQuery = true)
    Optional<Movie> findByTitle(String title);

    @Modifying
    @Query(value = "DELETE FROM movie WHERE title = ?1", nativeQuery = true)
    void deleteByTitle(String title);
}
