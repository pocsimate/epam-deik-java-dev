package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Screening;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-ci.properties")
class ScreeningRepositoryTest {

    @Autowired
    ScreeningRepository underTest;

    Screening screening;


    @Test
    public void testFindScreeningsInRoomOnDayShouldReturnListOfScreeningsOnGivenDayWhenExists() {
        // Given

        // When

        // Then
    }
}