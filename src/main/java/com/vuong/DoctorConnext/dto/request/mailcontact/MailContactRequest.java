package com.vuong.DoctorConnext.dto.request.mailcontact;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

