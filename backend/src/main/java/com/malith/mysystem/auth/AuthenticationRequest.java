package com.malith.mysystem.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
