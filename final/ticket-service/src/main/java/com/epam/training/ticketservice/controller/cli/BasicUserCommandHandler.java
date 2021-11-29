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

        if (!Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            return "Already signed in. Sign out first";
        }

        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            return "Authentication success";
        } catch (AuthenticationException e) {
            return "Signing in failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign user out", key = "sign out")
    public String signOut(){
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            return "You are not signed in";
        } else {
            SecurityContextHolder.clearContext();
            return "Signed out successfully";
        }
    }

    @ShellMethod(value = "Get signed in user data", key = "describe account")
    public String me(){
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            return "You are not signed in";
        } else {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
    }
}