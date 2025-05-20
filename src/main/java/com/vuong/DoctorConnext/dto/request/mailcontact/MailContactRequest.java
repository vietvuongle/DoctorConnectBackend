package com.vuong.DoctorConnext.dto.request.mailcontact;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailContactRequest {
    String name;
    String email;
    String phone;
    String message;

}

