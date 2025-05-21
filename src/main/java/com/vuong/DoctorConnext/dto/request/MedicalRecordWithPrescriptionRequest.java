package com.vuong.DoctorConnext.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordWithPrescriptionRequest {
    String patientId;
    String doctorId;

    String symptoms;
    String diagnosis;
    String examination;
    String treatment;
    String notes;
    List<PrescriptionRequest> prescriptions;
}
