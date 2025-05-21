package com.vuong.DoctorConnext.dto.response.user;

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
    String image;
    String address;
    String gender;
    String dob;
    String phone;
    Set<String> roles;
}
