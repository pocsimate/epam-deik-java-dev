package com.epam.training.ticketservice.controller.cli;

import com.epam.training.ticketservice.exception.UserAlreadyExistsException;
import com.epam.training.ticketservice.service.CliUserService;
import com.epam.training.ticketservice.util.AuthorityCheckerUtil;
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

    private CliUserService cliUserService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public BasicUserCommandHandler(CliUserService cliUserService, AuthenticationManager authenticationManager) {
        this.cliUserService = cliUserService;
        this.authenticationManager = authenticationManager;
    }

    @ShellMethod(value = "Register user", key = "sign up")
    public String signUp(String username, String password){
        try {
            cliUserService.registerUser(username, new BCryptPasswordEncoder().encode(password));
            return "Registered successfully";
        } catch (UserAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }

    // TODO impelement privileged well.
    @ShellMethod(value = "Log user in", key = "sign in")
    public String signIn(@ShellOption("privileged") boolean privileged, String username, String password){

        if (privileged) {
            System.out.println("privileged command");
        }

        if (!Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            return "Already logged in. Log out first";
        }

        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            return "Login success";
        } catch (AuthenticationException e) {
            return "Login failed due to incorrect credentials\n";
        }
    }

    @ShellMethod(value = "Log user out", key = "sign out")
    public String signOut(){
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            return "You are not logged in";
        } else {
            SecurityContextHolder.clearContext();
            return "Logged out successfully";
        }
    }

    @ShellMethod(value = "Get logged in user data", key = "describe account")
    public String me(){
        if (!AuthorityCheckerUtil.isAuthorized()) {
            return "You are not signed in";
        } else {
            return String.format("Signed in with %s %s", AuthorityCheckerUtil.isAdminAuthorized()? "privileged account" : "account",
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}