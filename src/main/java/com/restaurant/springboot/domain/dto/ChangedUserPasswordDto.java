package com.restaurant.springboot.domain.dto;

import javax.validation.constraints.Size;

public class ChangedUserPasswordDto {

    private String login;
    private String oldPassword;
    @Size(min = 10, max = 50)
    private String newPassword;

    public ChangedUserPasswordDto() {
    }

    public ChangedUserPasswordDto( String login, String oldPassword, @Size(min = 10, max = 50) String newPassword) {
        this.login = login;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}