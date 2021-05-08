package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.*;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.model.AuthorizationStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserByLogin(String login);

    User getUserById(Long id);

    HttpStatus registerUser(CreateUserDto createUser);

    AuthorizationStatus checkLogin(UserAuthorizationDto userVerification);

    boolean deleteUser (DeleteUserDto deleteUser);

    boolean updateUserLogin(ChangedUserLoginDto changedUserLogin);

    boolean updateUserPassword(ChangedUserPasswordDto changedUserPassword);

    boolean updateUserPhoneNumber(ChangedPhoneNumberDto changedPhoneNumberDto);

    Long getUserIdByLogin(String login);

}
