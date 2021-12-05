package com.epam.training.ticketservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cli_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CliUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private CliUserRole cliUserRole = CliUserRole.USER;

    public CliUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CliUserRole getCliUserRole() {
        return cliUserRole;
    }

    public void setCliUserRole(CliUserRole cliUserRole) {
        this.cliUserRole = cliUserRole;
    }
}
