package com.vuong.DoctorConnext.dto.response;

import com.vuong.DoctorConnext.dto.request.PrescriptionRequest;
import com.vuong.DoctorConnext.entity.MedicalRecord;
import com.vuong.DoctorConnext.entity.Prescription;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordWithPrescriptionResponse {
    MedicalRecord medicalRecord;
    List<Prescription> prescriptions;
}
