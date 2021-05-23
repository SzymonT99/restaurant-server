package com.restaurant.springboot.domain.dto;

import javax.validation.constraints.Email;

public class ChangedEmailDto {

    private String login;
    private String password;
    @Email
    private String newEmail;

    public ChangedEmailDto() {
    }

    public ChangedEmailDto(String login, String password,  @Email String newEmail) {
        this.login = login;
        this.password = password;
        this.newEmail = newEmail;
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

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
