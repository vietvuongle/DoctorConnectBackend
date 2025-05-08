package com.vuong.DoctorConnext.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID") // USERNAME_INVALID is key define in ErrorCode
    String name;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    String email;
}