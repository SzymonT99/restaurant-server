package com.restaurant.springboot.domain.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ChangedPhoneNumberDto {

    private String login;
    private String password;
    @Positive
    @Size(min = 9, max = 9)
    private String newPhoneNumber;

    public ChangedPhoneNumberDto() {
    }

    public ChangedPhoneNumberDto(String login, String password, @Positive @Size(min = 9, max = 9) String newPhoneNumber) {
        this.login = login;
        this.password = password;
        this.newPhoneNumber = newPhoneNumber;
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

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
