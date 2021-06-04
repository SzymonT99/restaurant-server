package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.controller.UserApiController;
import com.restaurant.springboot.domain.dto.*;
import com.restaurant.springboot.domain.entity.ConfirmationToken;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.entity.UserToken;
import com.restaurant.springboot.domain.model.AuthorizationStatus;
import com.restaurant.springboot.domain.repository.ConfirmationTokenRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.domain.repository.UserTokenRepository;
import com.restaurant.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final Integer MAX_LOGIN_ATTEMPTS = 5;

    private final UserRepository userRepository;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender javaMailSender;
    private final UserTokenRepository userTokenRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository,
                           JavaMailSender javaMailSender, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.javaMailSender = javaMailSender;
        this.userTokenRepository = userTokenRepository;
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

        if (userRepository.existsByLogin(createUser.getLogin())
                || userRepository.existsByEmail(createUser.getEmail())) {
            return HttpStatus.FORBIDDEN;
        }

        Set<ConstraintViolation<CreateUserDto>> violations = validator.validate(createUser);
        for (ConstraintViolation<CreateUserDto> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
        if (!violations.isEmpty()) {            // walidacja poprawności formatu email i rozmiarów loginu i hasła
            return HttpStatus.BAD_REQUEST;
        }

        if (createUser.getLogin() != null && createUser.getEmail() != null
                && createUser.getPassword() != null && createUser.getPhoneNumber() != null) {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            User user = new User(createUser.getLogin(), bCryptPasswordEncoder.encode(createUser.getPassword()),
                    createUser.getEmail(), createUser.getPhoneNumber(), true, 0, false);

            userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            // Wysyłanie emaila z kodem aktywujacym konto
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Restaurant App - Weryfikacja adresu email.");
            mailMessage.setFrom("restaurant.toik2021@gmail.com");
            mailMessage.setSentDate(new Date());
            mailMessage.setText("Aby zweryfikować wprowadzony email wciśnij podany link: \n"
                    +"http://localhost:8080/restaurant/account-activation?token=" + confirmationToken.getToken());

            javaMailSender.send(mailMessage);

            return HttpStatus.CREATED;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ModelAndView activateAccount(ModelAndView modelAndView, String token) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if(confirmationToken != null) {
            User user = userRepository.findByLogin(confirmationToken.getUser().getLogin());
            if (user.isAccountVerification()) {
                modelAndView.addObject("message", "Konto zostało już aktywowane.");
                modelAndView.setViewName("WrongActivation");
            }
            else {
                user.setAccountVerification(true);
                userRepository.save(user);
                modelAndView.addObject("login",user.getLogin());
                modelAndView.setViewName("SuccessfulActivation");
            }
        }
        else {
            modelAndView.addObject("message", "Podaj poprawny kod aktywujący konto.");
            modelAndView.setViewName("WrongActivation");
        }

        return modelAndView;
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

            if (bCryptPasswordEncoder.matches(userVerification.getPassword(), user.getPassword())
                    && user.getIncorrectLoginCounter() < MAX_LOGIN_ATTEMPTS) {

                user.setIncorrectLoginCounter(0);
                userRepository.save(user);

                return AuthorizationStatus.ACCESS;
            }
        }

        return AuthorizationStatus.UNAUTHORIZED;
    }

    @Override
    public boolean deleteUser(DeleteUserDto deleteUser) {

        if (deleteUser.getLogin() == null || deleteUser.getPassword() == null) return false;

        if (userRepository.existsByLogin(deleteUser.getLogin())) {

            User user = userRepository.findByLogin(deleteUser.getLogin());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!bCryptPasswordEncoder.matches(deleteUser.getPassword(), user.getPassword())) {
                return false;
            } else {

                userRepository.deleteByLogin(deleteUser.getLogin());
                return true;

            }
        }
        return false;
    }

    @Override
    public boolean updateLogin(ChangedUserLoginDto changedUserLogin) {

        if (changedUserLogin.getOldLogin() == null || changedUserLogin.getNewLogin() == null ||
                changedUserLogin.getPassword() == null) return false;

        Set<ConstraintViolation<ChangedUserLoginDto>> violations = validator.validate(changedUserLogin);
        for (ConstraintViolation<ChangedUserLoginDto> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
        if (!violations.isEmpty()) {            // walidacja poprawności formatu nowego loginu
            return false;
        }

        if (userRepository.existsByLogin(changedUserLogin.getOldLogin())) {

            User user = userRepository.findByLogin(changedUserLogin.getOldLogin());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!bCryptPasswordEncoder.matches(changedUserLogin.getPassword(), user.getPassword())) {
                return false;
            } else {
                user.setLogin(changedUserLogin.getNewLogin());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(ChangedUserPasswordDto changedUserPassword) {

        if (changedUserPassword.getLogin() == null || changedUserPassword.getOldPassword() == null ||
        changedUserPassword.getNewPassword() == null) return false;

        Set<ConstraintViolation<ChangedUserPasswordDto>> violations = validator.validate(changedUserPassword);
        for (ConstraintViolation<ChangedUserPasswordDto> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
        if (!violations.isEmpty()) {            // walidacja poprawności formatu nowego hasła
            return false;
        }

        if (userRepository.existsByLogin(changedUserPassword.getLogin())) {

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
        return false;
    }

    @Override
    public boolean updatePhoneNumber(ChangedPhoneNumberDto changedPhoneNumberDto) {

        if (changedPhoneNumberDto.getLogin() == null || changedPhoneNumberDto.getPassword() == null ||
        changedPhoneNumberDto.getNewPhoneNumber() == null) return  false;

        Set<ConstraintViolation<ChangedPhoneNumberDto>> violations = validator.validate(changedPhoneNumberDto);
        for (ConstraintViolation<ChangedPhoneNumberDto> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
        if (!violations.isEmpty()) {            // walidacja poprawności formatu nowego numeru telefonu
            return false;
        }

        if (userRepository.existsByLogin(changedPhoneNumberDto.getLogin())) {

            User user = userRepository.findByLogin(changedPhoneNumberDto.getLogin());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!bCryptPasswordEncoder.matches(changedPhoneNumberDto.getPassword(), user.getPassword())) {
                return false;
            } else {
                user.setPhoneNumber(changedPhoneNumberDto.getNewPhoneNumber());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateEmail(ChangedEmailDto changedEmailDto) {

        if (changedEmailDto.getLogin() == null || changedEmailDto.getPassword() == null ||
        changedEmailDto.getNewEmail() == null) return false;

        Set<ConstraintViolation<ChangedEmailDto>> violations = validator.validate(changedEmailDto);
        for (ConstraintViolation<ChangedEmailDto> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
        if (!violations.isEmpty()) {            // walidacja poprawności formatu email
            return false;
        }

        if (userRepository.existsByLogin(changedEmailDto.getLogin())) {

            User user = userRepository.findByLogin(changedEmailDto.getLogin());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!bCryptPasswordEncoder.matches(changedEmailDto.getPassword(), user.getPassword())) {
                return false;
            } else {
                user.setEmail(changedEmailDto.getNewEmail());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public Long getUserIdByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return user.getUserId();
    }

    @Override
    public boolean logout(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return false;
        UserToken userToken = userTokenRepository.findByUser(user);
        userToken.setActive(false);
        userTokenRepository.save(userToken);
        return true;
    }
}
