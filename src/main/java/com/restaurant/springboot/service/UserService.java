package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.*;
import com.restaurant.springboot.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserByLogin(String login);

    User getUserById(Long id);

    HttpStatus registerUser(CreateUserDto createUser);

    Message checkLogin(UserAuthorizationDto userVerification);

    boolean deleteUser (DeleteUserDto deleteUser);

    boolean updateLogin(ChangedUserLoginDto changedUserLogin);

    boolean updatePassword(ChangedUserPasswordDto changedUserPassword);

    boolean updatePhoneNumber(ChangedPhoneNumberDto changedPhoneNumberDto);

    boolean updateEmail(ChangedEmailDto changedEmailDto);

    Long getUserIdByLogin(String login);

    ModelAndView activateAccount(ModelAndView modelAndView, String confirmationToken);

    boolean logout(Long userId);
}
