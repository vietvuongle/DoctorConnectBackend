package com.vuong.DoctorConnext.dto.response.doctor;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorResponse {
    String id;
    String name;
    String email;
    String image;
    String fees;
    String clinicId;
    String speciality;
    String about;
    String degree;
    String sex;
    String phone;
    String address;
    Set<String> roles;
}

