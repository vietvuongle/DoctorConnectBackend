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
public class DoctorUpdateRequest {
    String id;
    String name;
    String email;
    MultipartFile image;
    String experience;
    String fees;
    String speciality;
    String about;
    String clinicId;
    String degree;
    String sex;
    String phone;
    String address;
    String school;
}
