package com.epam.training.ticketservice.repository;

import java.util.Optional;

import com.epam.training.ticketservice.model.CliUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-ci.properties")
class CliUserRepositoryTest {

    @Autowired
    private CliUserRepository underTest;

    CliUser cliUser;

    @Test
    public void testFindByUsernameShouldReturnTheUserByUsernameIfExists() {
        // Given
        cliUser = new CliUser("testUsername", "testPassword");
        underTest.save(cliUser);
        Optional<CliUser> dbUser;

        // When
        dbUser = underTest.findByUsername(cliUser.getUsername());

        //then
        Assertions.assertTrue(dbUser.isPresent());
    }

    @Test
    public void testFindByUsernameShouldReturnEmptyOptionalIfNotExists() {
        // Given
        cliUser = new CliUser("asd", "das");
        underTest.delete(cliUser);
        Optional<CliUser> dbUser;

        // When
        dbUser = underTest.findByUsername(cliUser.getUsername());

        // Then
        Assertions.assertTrue(dbUser.isEmpty());
    }
}