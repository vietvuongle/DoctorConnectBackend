package com.vuong.DoctorConnext.controller;
import com.vuong.DoctorConnext.dto.request.mailcontact.MailContactRequest;
import com.vuong.DoctorConnext.service.MailContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/contact")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5175")
//@CrossOrigin(origins = "*") // Cho phép gọi từ frontend React
public class MailContactController {

    private MailContactService contactService;

    @PostMapping
    public ResponseEntity<String> sendContact(@RequestBody MailContactRequest request) {
        contactService.sendContactMail(request);
        return ResponseEntity.ok("Gửi thành công");
    }
}
