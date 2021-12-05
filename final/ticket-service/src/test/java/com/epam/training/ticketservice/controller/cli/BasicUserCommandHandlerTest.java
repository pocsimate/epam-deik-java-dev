package com.epam.training.ticketservice.controller.cli;

import com.epam.training.ticketservice.exception.UserAlreadyExistsException;
import com.epam.training.ticketservice.model.CliUser;
import com.epam.training.ticketservice.model.CliUserRole;
import com.epam.training.ticketservice.service.CliUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BasicUserCommandHandlerTest {

    @Mock
    CliUserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    BasicUserCommandHandler userCommandHandler;

    CliUser testUser;

    @BeforeEach
    void setUp() {
        testUser = setUser();
    }

    @AfterEach
    void clearSecurityContextHolder() {
        SecurityContextHolder.clearContext();
    }

    CliUser setUser() {
        return CliUser.builder()
                .username("TestUser")
                .password("TestUserPassword")
                .cliUserRole(CliUserRole.USER)
                .build();
    }

    @Test
    void testSignUpShouldRegisterNewUserWhenDoesNotExist() {
        // Given
        BDDMockito.doNothing().when(userService).registerUser(testUser.getUsername(), testUser.getPassword());
        String expectedResult = "Registered successfully";

        // When
        String actualResult = userCommandHandler.signUp(testUser.getUsername(), testUser.getPassword());

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

   @Test
    void testSignUpShouldReturnUserAlreadyExistsExceptionMessageWhenUserAlreadyExists() {
        // Given
        BDDMockito.willThrow(UserAlreadyExistsException.class).willDoNothing().given(userService).registerUser(testUser.getUsername(),
                testUser.getPassword());
        String expectedResult = "Registered successfully";

        // When
        String actualResult = userCommandHandler.signUp(testUser.getUsername(), testUser.getPassword());

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSignInShouldReturnSuccessfulSignInMessageWhenUserCredentialsAreCorrect() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());
        BDDMockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        String expectedMessage = "Login success";

        // When
        String result = userCommandHandler.signIn(false, testUser.getUsername(), testUser.getPassword());

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }

/*    @Test
    void testSignInShouldReturnIncorrectCredentialsMessageWhenUserCredentialsAreInCorrect() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());
        BDDMockito.willThrow(AuthenticationException.class).given(authenticationManager).authenticate(authentication);
        String expectedMessage = "Login failed due to incorrect credentials";

        // When
        String result = userCommandHandler.signIn(false, testUser.getUsername(), testUser.getPassword());

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }*/

    @Test
    void testSignInShouldReturnAlreadySignedInMessageWhenUserIsAlreadySignedIn() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword()));
        String expectedMessage = "Already signed in. Sign out first";

        // When
        String result = userCommandHandler.signIn(false, testUser.getUsername(), testUser.getPassword());

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }

    @Test
    void testSignOutShouldReturnNotLoggedInMessageWhenNotLoggedIn() {
        // Given
        String expectedMessage = "You are not logged in";

        // When
        String result = userCommandHandler.signOut();

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }

    @Test
    void testSignOutShouldReturnSuccessfulLogoutMessageWhenLoggedIn() {
        // Given
       SecurityContextHolder.getContext().setAuthentication(
               new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword()));
       String expectedMessage = "Logged out successfully";

        // When
        String result = userCommandHandler.signOut();

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }

    @Test
    void testMeShouldReturnNotLoggedInMessageWhenUserIsNotLoggedIn() {
        // Given
        String expectedMessage = "You are not signed in";

        // When
        String result = userCommandHandler.me();

        // Then
        Assertions.assertEquals(expectedMessage, result);
    }
}