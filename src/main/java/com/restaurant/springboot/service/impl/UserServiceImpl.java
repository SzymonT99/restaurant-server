package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.*;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.model.AuthorizationStatus;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Integer MAX_LOGIN_ATTEMPTS = 5;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public HttpStatus registerUser(CreateUserDto createUser) {

        if (userRepository.existsByLogin(createUser.getLogin())) {
            return HttpStatus.FORBIDDEN;
        }

        if (createUser.getLogin() != null && createUser.getEmail() != null && createUser.getPassword() != null) {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            User user = new User(createUser.getLogin(), bCryptPasswordEncoder.encode(createUser.getPassword()),
                    createUser.getEmail(), createUser.getPhoneNumber(), true, 0, true);

            userRepository.save(user);

            return HttpStatus.CREATED;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public AuthorizationStatus checkLogin(UserAuthorizationDto userVerification) {

        if (userRepository.existsByLogin(userVerification.getLogin())) {

            User user = userRepository.findByLogin(userVerification.getLogin());

            if (!user.isActive()) {
                return AuthorizationStatus.FORBIDDEN;
            }

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!bCryptPasswordEncoder.matches(userVerification.getPassword(), user.getPassword())) {

                user.setIncorrectLoginCounter(user.getIncorrectLoginCounter() + 1);
                user.setActive(user.getIncorrectLoginCounter() < MAX_LOGIN_ATTEMPTS);
                userRepository.save(user);

                return AuthorizationStatus.UNAUTHORIZED;
            }

            if (bCryptPasswordEncoder.matches(userVerification.getPassword(),
                    user.getPassword()) && user.getIncorrectLoginCounter() < MAX_LOGIN_ATTEMPTS) {

                user.setIncorrectLoginCounter(0);
                userRepository.save(user);

                return AuthorizationStatus.ACCESS;
            }
        }

        return AuthorizationStatus.UNAUTHORIZED;
    }

    @Override
    public boolean deleteUser(DeleteUserDto deleteUser) {

        User user = userRepository.findByLogin(deleteUser.getLogin());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(deleteUser.getPassword(), user.getPassword())) {
            return false;
        } else {

            userRepository.deleteByLogin(deleteUser.getLogin());
            return true;

        }
    }

    @Override
    public boolean updateLogin(ChangedUserLoginDto changedUserLogin) {

        User user = userRepository.findByLogin(changedUserLogin.getOldLogin());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(changedUserLogin.getPassword(), user.getPassword())) {
            return false;
        }
        else {
            user.setLogin(changedUserLogin.getNewLogin());
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean updatePassword(ChangedUserPasswordDto changedUserPassword) {

        User user = userRepository.findByLogin(changedUserPassword.getLogin());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(changedUserPassword.getOldPassword(), user.getPassword())) {
            return false;
        } else {

            user.setPassword(bCryptPasswordEncoder.encode(changedUserPassword.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean updatePhoneNumber(ChangedPhoneNumberDto changedPhoneNumberDto) {

        User user = userRepository.findByLogin(changedPhoneNumberDto.getLogin());

        if (userRepository.existsByPhoneNumber(changedPhoneNumberDto.getOldPhoneNumber())) {
            user.setPhoneNumber(changedPhoneNumberDto.getNewPhoneNumber());
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public Long getUserIdByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        return user.getUserId();
    }
}
