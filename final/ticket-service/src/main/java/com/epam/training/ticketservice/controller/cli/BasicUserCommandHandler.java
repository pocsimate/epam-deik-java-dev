package com.epam.training.ticketservice.controller.cli;

import com.epam.training.ticketservice.exception.UserAlreadyExistsException;
import com.epam.training.ticketservice.service.CliUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Objects;

@ShellComponent
public class BasicUserCommandHandler {

    @Autowired
    CliUserService cliUserService;

    @Autowired
    AuthenticationManager authenticationManager;

    @ShellMethod(value = "Register user", key = "sign up")
    public String signUp(String username, String password){
        try {
            cliUserService.registerUser(username, new BCryptPasswordEncoder().encode(password));
            return "Registered successfully";
        } catch (UserAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethod(value = "Login user", key = "sign in")
    public String signIn(@ShellOption("privileged") boolean privileged, String username, String password){

        if (privileged) {
            System.out.println("privileged command");
        }

        System.out.println("pw: " + password + ", user: " + username + ", privileged: " + privileged);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.isNull(authentication)) {
            return "Already signed in. Sign out first";
        }

        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            return "Authentication success";
        } catch (AuthenticationException e) {
            return "Login failed due to incorrect credentials";
        }
    }
}