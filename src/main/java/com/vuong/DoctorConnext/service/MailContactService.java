package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.mailcontact.MailContactRequest;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailContactService {

    JavaMailSender mailSender;

    public void sendContactMail(MailContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("xuanlinhdev.2208@gmail.com"); // Hoặc cấu hình riêng
        message.setSubject("Tin nhắn liên hệ từ: " + request.getName());

        String body = String.format(
                "Tên: %s\nEmail: %s\nSố điện thoại: %s\n\nNội dung:\n%s",
                request.getName(), request.getEmail(), request.getPhone(), request.getMessage()
        );

        message.setText(body);
        mailSender.send(message);
    }
}

