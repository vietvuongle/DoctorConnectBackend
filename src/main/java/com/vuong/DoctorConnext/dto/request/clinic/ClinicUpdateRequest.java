package com.vuong.DoctorConnext.dto.request.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClinicUpdateRequest {
    String id;
    String name;
    String email;
    String address;
    MultipartFile image;
    String description;
}
