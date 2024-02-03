package com.atahf.flightsearchapi.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

import static com.atahf.flightsearchapi.security.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(
            AIRPORT_CREATE,
            AIRPORT_READ,
            AIRPORT_UPDATE,
            AIRPORT_Delete,
            FLIGHT_CREATE,
            FLIGHT_READ,
            FLIGHT_UPDATE,
            FLIGHT_Delete
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
