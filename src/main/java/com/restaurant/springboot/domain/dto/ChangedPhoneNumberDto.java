package com.restaurant.springboot.domain.dto;

public class ChangedPhoneNumberDto {

    private String login;
    private String oldPhoneNumber;
    private String newPhoneNumber;

    public ChangedPhoneNumberDto() {
    }

    public ChangedPhoneNumberDto(String login, String oldPhoneNumber, String newPhoneNumber) {
        this.login = login;
        this.oldPhoneNumber = oldPhoneNumber;
        this.newPhoneNumber = newPhoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
