package com.restaurant.springboot.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class CreateUserDto {

    @Size(min = 4, max = 20)
    private String login;

    @Email
    private String email;

    private String phoneNumber;

    @Size(min = 10, max = 50)
    private String password;

    public CreateUserDto() {
    }

    public CreateUserDto(@Size(min = 4, max = 20) String login, @Email String email, String phoneNumber,
                         @Size(min = 10, max = 50) String password) {
        this.login = login;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
