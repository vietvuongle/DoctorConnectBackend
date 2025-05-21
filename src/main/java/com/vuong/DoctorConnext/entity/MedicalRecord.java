package com.vuong.DoctorConnext.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String patientId;
    String doctorId;

    String symptoms;
    String diagnosis;
    String examination;
    String treatment;
    String notes;
    String createdAt;
}
