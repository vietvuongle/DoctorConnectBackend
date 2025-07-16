package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.mailcontact.MailContactRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailContactService {

    JavaMailSender mailSender;

    public void sendContactMail(MailContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("vietvuongf@gmail.com"); // Email đích của bạn
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

    public void sendMedicalRecordEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vietvuongf@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
