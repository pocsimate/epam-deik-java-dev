package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.CliUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CliUserRepository extends JpaRepository<CliUser, Integer> {
    Optional<CliUser> findByUsername(String username);
}
