package com.example.email_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JavaMailSender emailSender;

    public int getVerificationCode(String email) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(email);
        ResponseEntity<Integer> response = restTemplate.postForEntity("http://localhost:8080/email/get-verification-code", request, Integer.class);
        return response.getBody();
    }



    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
