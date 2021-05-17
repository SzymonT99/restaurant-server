package com.restaurant.springboot.domain.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String token;
    private final Long userId;

    public JwtResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}