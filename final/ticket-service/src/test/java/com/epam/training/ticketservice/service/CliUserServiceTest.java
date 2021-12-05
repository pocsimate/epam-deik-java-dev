package com.epam.training.ticketservice.service;

import java.util.Objects;
import java.util.Optional;

import com.epam.training.ticketservice.exception.UserAlreadyExistsException;
import com.epam.training.ticketservice.model.CliUser;
import com.epam.training.ticketservice.model.CliUserRole;
import com.epam.training.ticketservice.repository.CliUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CliUserServiceTest {

    @Mock CliUserRepository userRepository;

    @InjectMocks
    CliUserService userService;

    CliUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new CliUser();
        testUser.setUsername("test_user");
        testUser.setPassword("test_user_password");
        testUser.setCliUserRole(CliUserRole.USER);
    }

    @Test
    void testGetUserByUsernameShouldReturnUserInOptionalWhenExists() {
        // Given
        BDDMockito.given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.of(testUser));

        // When
        Optional<CliUser> optionalUser = userService.getUserByUsername(testUser.getUsername());

        // Then
        Assertions.assertEquals(testUser.getUsername(), optionalUser.get().getUsername());
    }

    @Test
    void testRegisterUserShouldRegisterUserWhenDoesNotExist() {
        // Given
        BDDMockito.given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.empty());

        // When
        userService.registerUser(testUser.getUsername(), testUser.getPassword());

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testRegisterUserShouldThrowUserAlreadyExistsExceptionWhenUserAlreadyExists() {
        // Given
        BDDMockito.given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.of(testUser));

        // When - Then
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(testUser.getUsername(), testUser.getPassword()));
    }

    @Test
    void testLoadUserByUsernameShouldReturnUserDetailsWhenUsernameExists() {
        // Given
        BDDMockito.given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userService.loadUserByUsername(testUser.getUsername());

        // Then
        Assertions.assertTrue(Objects.nonNull(userDetails));
    }

    @Test
    void testLoadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUsernameNotExists() {
        // Given
        BDDMockito.given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(testUser.getUsername()));

    }
}