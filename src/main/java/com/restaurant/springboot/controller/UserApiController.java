package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.*;
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

        LOGGER.info("--- delete user from data base");
        LOGGER.info("--- login: {}", deleteUserDto.getLogin());

        boolean deletedStatus = userService.deleteUser(deleteUserDto);

        return deletedStatus
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        LOGGER.info("--- get all users");

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/user-update/login")
    public ResponseEntity<Void> updateLogin(@Valid @RequestBody ChangedUserLoginDto changedUserLoginDto) {

        LOGGER.info("--- update user login");
        LOGGER.info("--- old login: {}", changedUserLoginDto.getOldLogin());
        LOGGER.info("--- new login: {}", changedUserLoginDto.getNewLogin());

        boolean status = userService.updateUserLogin(changedUserLoginDto);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user-update/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody ChangedUserPasswordDto changedUserLoginDto) {

        LOGGER.info("--- update user password");
        LOGGER.info("--- login: {}", changedUserLoginDto.getLogin());

        boolean status = userService.updateUserPassword(changedUserLoginDto);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user-update/phone")
    public ResponseEntity<Void> updatePhoneNumber(@Valid @RequestBody ChangedPhoneNumberDto changedPhoneNumberDto) {

        LOGGER.info("--- update user phone number");
        LOGGER.info("--- login: {}", changedPhoneNumberDto.getLogin());
        LOGGER.info("--- old phone number: {}", changedPhoneNumberDto.getOldPhoneNumber());
        LOGGER.info("--- new phone number: {}", changedPhoneNumberDto.getNewPhoneNumber());

        boolean status = userService.updateUserPhoneNumber(changedPhoneNumberDto);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}