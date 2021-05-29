package com.restaurant.springboot.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendVerificationEmail(SimpleMailMessage email);

}
