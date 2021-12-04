package com.epam.training.ticketservice.controller.cli.authorized;

import com.epam.training.ticketservice.util.AuthorityCheckerUtil;
import org.springframework.shell.Availability;

public abstract class AuthorizedCommand {
    public Availability isAuthorized() {
        if (AuthorityCheckerUtil.isAuthorized()) {
            return Availability.available();
        }
        return Availability.unavailable("you are not signedIn. Please sign in to be able to use this command!");
    }
    public Availability isAdminAuthorized() {
        if (AuthorityCheckerUtil.isAdminAuthorized()) {
            return Availability.available();
        }
        return Availability.unavailable("you have insufficient privileges to run this command!");
    }
}
