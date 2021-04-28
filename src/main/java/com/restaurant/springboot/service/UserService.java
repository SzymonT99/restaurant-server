package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.CreateUserDto;
import com.restaurant.springboot.domain.dto.DeleteUserDto;
import com.restaurant.springboot.domain.dto.UserAuthorizationDto;
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

}
