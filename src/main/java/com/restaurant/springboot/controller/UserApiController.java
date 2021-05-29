package com.restaurant.springboot.controller;

import com.restaurant.springboot.config.JwtTokenUtil;
import com.restaurant.springboot.domain.dto.*;
import com.restaurant.springboot.domain.entity.ConfirmationToken;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.model.AuthorizationStatus;
import com.restaurant.springboot.domain.repository.ConfirmationTokenRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.EmailService;
import com.restaurant.springboot.service.UserService;
import com.restaurant.springboot.service.impl.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class UserApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;


    @Autowired
    public UserApiController(UserService userService, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto createUserDto) {

        LOGGER.info("--- login: {}", createUserDto.getLogin());
        LOGGER.info("--- e-mail: {}", createUserDto.getEmail());
        LOGGER.info("--- phone number: {}", createUserDto.getPhoneNumber());

        HttpStatus code = userService.registerUser(createUserDto);

        return new ResponseEntity<>(code);
    }

    @GetMapping("/account-activation")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String token) {

        return userService.activateAccount(modelAndView, token);
    }


    @PostMapping("/user/login")
    public ResponseEntity<?> authorizeUser(@RequestBody UserAuthorizationDto userVerificationDto) throws Exception {

        LOGGER.info("--- check login data: {}", userVerificationDto.getLogin());
        LOGGER.info("--- check password data: {}", userVerificationDto.getPassword());

        AuthorizationStatus status = userService.checkLogin(userVerificationDto);
        LOGGER.info("--- login status: {}", status);

        if (status == AuthorizationStatus.ACCESS) {

            Long userId = userService.getUserIdByLogin(userVerificationDto.getLogin());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userVerificationDto.getLogin());
            final String token = jwtTokenUtil.generateToken(userDetails);
            JwtResponse jwtResponse = new JwtResponse(token, userId);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

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
    public ResponseEntity<?> updateLogin(@Valid @RequestBody ChangedUserLoginDto changedUserLoginDto) {

        LOGGER.info("--- update user login");
        LOGGER.info("--- old login: {}", changedUserLoginDto.getOldLogin());
        LOGGER.info("--- new login: {}", changedUserLoginDto.getNewLogin());

        boolean status = userService.updateLogin(changedUserLoginDto);
        if (status) {

            Long userId = userService.getUserIdByLogin(changedUserLoginDto.getNewLogin());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(changedUserLoginDto.getNewLogin());
            final String token = jwtTokenUtil.generateToken(userDetails);
            JwtResponse jwtResponse = new JwtResponse(token, userId);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/user-update/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangedUserPasswordDto changedUserPasswordDto) {

        LOGGER.info("--- update user password");
        LOGGER.info("--- login: {}", changedUserPasswordDto.getLogin());

        boolean status = userService.updatePassword(changedUserPasswordDto);
        if (status) {
            Long userId = userService.getUserIdByLogin(changedUserPasswordDto.getLogin());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(changedUserPasswordDto.getLogin());
            final String token = jwtTokenUtil.generateToken(userDetails);
            JwtResponse jwtResponse = new JwtResponse(token, userId);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/user-update/phone")
    public ResponseEntity<Void> updatePhoneNumber(@Valid @RequestBody ChangedPhoneNumberDto changedPhoneNumberDto) {

        LOGGER.info("--- update user phone number");
        LOGGER.info("--- login: {}", changedPhoneNumberDto.getLogin());
        LOGGER.info("--- new phone number: {}", changedPhoneNumberDto.getNewPhoneNumber());

        boolean status = userService.updatePhoneNumber(changedPhoneNumberDto);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user-update/email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody ChangedEmailDto changedEmailDto) {

        LOGGER.info("--- update user email");
        LOGGER.info("--- login: {}", changedEmailDto.getLogin());
        LOGGER.info("--- new email: {}", changedEmailDto.getNewEmail());

        boolean status = userService.updateEmail(changedEmailDto);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}