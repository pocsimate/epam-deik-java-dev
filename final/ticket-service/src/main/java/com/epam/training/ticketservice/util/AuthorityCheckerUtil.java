package com.epam.training.ticketservice.util;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorityCheckerUtil {

    public static boolean isAdminAuthorized() {
        if (isAuthorized()) {
            GrantedAuthority admin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .filter(role -> "ROLE_ADMIN".equals(role.getAuthority()))
                    .findAny()
                    .orElse(null);

            return Objects.nonNull(admin);
        } else {
            return false;
        }
    }

    public static boolean isAuthorized() {
        return Objects.nonNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
