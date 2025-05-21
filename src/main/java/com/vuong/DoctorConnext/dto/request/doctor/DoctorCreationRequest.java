package com.vuong.DoctorConnext.dto.request.doctor;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorCreationRequest {
    String name;
    String email;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    MultipartFile image;
    String experience;
    String fees;
    String speciality;
    String about;
    String degree;
    String sex;
    String phone;
    String address;
    String school;
}
