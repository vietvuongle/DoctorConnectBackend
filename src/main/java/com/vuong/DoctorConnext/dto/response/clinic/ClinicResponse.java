package com.vuong.DoctorConnext.dto.response.clinic;


import com.vuong.DoctorConnext.enums.ClinicStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClinicResponse {
     String id;
     String name;
     String email;
     String image;
     String address;
     String description;
     ClinicStatus status;
}
