package com.vuong.DoctorConnext.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentCreationRequest {
    String name;
    String description;
    MultipartFile iconImage;

}
