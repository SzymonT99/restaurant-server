package com.restaurant.springboot.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "incorrect_login_counter")
    private Integer incorrectLoginCounter;

    @NotNull
    @Column(name = "account_verification")
    private boolean accountVerification;

    public User() {
    }

    public User(@NotNull String password, @NotNull @Email String email, @NotNull String phoneNumber,
                @NotNull boolean active, @NotNull Integer incorrectLoginCounter, @NotNull boolean accountVerification) {
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.incorrectLoginCounter = incorrectLoginCounter;
        this.accountVerification = accountVerification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getIncorrectLoginCounter() {
        return incorrectLoginCounter;
    }

    public void setIncorrectLoginCounter(Integer incorrectLoginCounter) {
        this.incorrectLoginCounter = incorrectLoginCounter;
    }

    public boolean isAccountVerification() {
        return accountVerification;
    }

    public void setAccountVerification(boolean accountVerification) {
        this.accountVerification = accountVerification;
    }
}
