package com.vuong.DoctorConnext.dto.request.password;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordChangeRequest {
    String currentPassword;
    String newPassword;
    String confirmNewPassword;
}
