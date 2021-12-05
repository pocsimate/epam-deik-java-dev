package com.epam.training.ticketservice;

import javax.annotation.PostConstruct;

import com.epam.training.ticketservice.model.CliUser;
import com.epam.training.ticketservice.model.CliUserRole;
import com.epam.training.ticketservice.repository.CliUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("ci")
public class AdminInitializer {

    private final CliUserRepository userRepository;

    @Autowired
    public AdminInitializer(CliUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initializeAdmin() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        CliUser admin = new CliUser();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setCliUserRole(CliUserRole.ADMIN);
        userRepository.save(admin);
    }
}
