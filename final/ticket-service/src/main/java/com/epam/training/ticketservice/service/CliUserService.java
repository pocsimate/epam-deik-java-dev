package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.UserAlreadyExistsException;
import com.epam.training.ticketservice.model.CliUser;
import com.epam.training.ticketservice.model.CliUserRole;
import com.epam.training.ticketservice.repository.CliUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CliUserService implements UserDetailsService {

    @Autowired
    CliUserRepository cliUserRepository;

    public void registerUser(String username, String password){
        if (getUserByUsername(username).isPresent()){
            throw new UserAlreadyExistsException(username);
        } else {
            CliUser cliUser = new CliUser(username, password);
            cliUserRepository.save(cliUser);
        }
    }

    public Optional<CliUser> getUserByUsername(String username){
        return cliUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CliUser> cliUser = getUserByUsername(username);

        if (cliUser.isPresent()) {
            User.UserBuilder userBuilder = User.builder();
            userBuilder
                    .username(cliUser.get().getUsername())
                    .password(cliUser.get().getPassword());

             if (cliUser.get().getCliUserRole().equals(CliUserRole.ADMIN)) {
                 userBuilder.roles("USER", "ADMIN");
             } else {
                 userBuilder.roles("USER");
             }

             return userBuilder.build();

        } else {
            throw new UsernameNotFoundException("No such user: " + username);
        }
    }
}
