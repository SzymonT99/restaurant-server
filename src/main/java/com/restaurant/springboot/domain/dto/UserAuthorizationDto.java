package com.restaurant.springboot.domain.dto;

public class UserAuthorizationDto {

    private String login;
    private String password;

    public UserAuthorizationDto() {
    }

    public UserAuthorizationDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAutorizationDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}