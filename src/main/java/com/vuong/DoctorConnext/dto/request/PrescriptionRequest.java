package com.vuong.DoctorConnext.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescriptionRequest {
    String medicine;
    String dosage;
    String frequency;
    String duration;
}
