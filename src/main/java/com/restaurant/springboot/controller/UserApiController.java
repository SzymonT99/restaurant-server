package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.CreateUserDto;
import com.restaurant.springboot.domain.dto.DeleteUserDto;
import com.restaurant.springboot.domain.dto.UserAuthorizationDto;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.model.AuthorizationStatus;
import com.restaurant.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class UserApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create-user")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto createUserDto){

        LOGGER.info("--- login: {}", createUserDto.getLogin());
        LOGGER.info("--- e-mail: {}", createUserDto.getEmail());
        LOGGER.info("--- phone number: {}", createUserDto.getPhoneNumber());
        //LOGGER.info("--- password: {}", createUserDto.getPassword());

        HttpStatus code =  userService.registerUser(createUserDto);

        return new ResponseEntity<>(code);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Void> authorizeUser(@RequestBody UserAuthorizationDto userVerificationDto) {

        LOGGER.info("--- check login data: {}", userVerificationDto.getLogin());

        AuthorizationStatus status = userService.checkLogin(userVerificationDto);


        if (status == AuthorizationStatus.ACCESS) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (status == AuthorizationStatus.UNAUTHORIZED) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserDto deleteUserDto) {

        boolean deletedStatus = userService.deleteUser(deleteUserDto);

        return deletedStatus
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

}