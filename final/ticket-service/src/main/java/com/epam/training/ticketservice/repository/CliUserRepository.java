package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.CliUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CliUserRepository extends JpaRepository<CliUser, Integer> {

    @Query(value = "SELECT * FROM cli_user u WHERE u.username = ?1", nativeQuery = true)
    Optional<CliUser> findByUsername(String username);
}
