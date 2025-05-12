package com.vuong.DoctorConnext.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String _id;
    String name;
    String email;
    String phone;
    String gender;
    String dob;
    String address;
    String imageUrl;
    Set<String> roles;
}
