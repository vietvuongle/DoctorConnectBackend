package com.vuong.DoctorConnext.dto.request.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClinicCreationRequest {
     String name;
     String email;
     String password;
     String address;
     MultipartFile image;
     String description;
}
