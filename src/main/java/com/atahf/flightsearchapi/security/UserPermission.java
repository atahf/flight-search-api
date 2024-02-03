package com.atahf.flightsearchapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {
    AIRPORT_CREATE("airport:create"),
    AIRPORT_READ("airport:read"),
    AIRPORT_UPDATE("airport:update"),
    AIRPORT_Delete("airport:delete"),
    FLIGHT_CREATE("flight:create"),
    FLIGHT_READ("flight:read"),
    FLIGHT_UPDATE("flight:update"),
    FLIGHT_Delete("flight:delete");


    private final String permission;
}
