package com.vuong.DoctorConnext.dto.response.department;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    String id;

    String name;
    String description;
    String iconImage;
    String about;
    Set<String> roles;
}
