package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.mailcontact.MailContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailContactService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactMail(MailContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("xuanlinhdev.2208@gmail.com"); // Email đích của bạn
        message.setSubject("Tin nhắn liên hệ từ: " + request.getName());

        String body = String.format(
                "Tên: %s\nEmail: %s\nSố điện thoại: %s\n\nNội dung:\n%s",
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getMessage()
        );

        message.setText(body);
        mailSender.send(message);
    }
}
