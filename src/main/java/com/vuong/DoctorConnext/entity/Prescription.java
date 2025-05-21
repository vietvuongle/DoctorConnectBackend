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
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String medicalRecordId;  // chỉ lưu id của MedicalRecord

    String medicineName;     // hoặc medicineId nếu bạn có bảng Medicine riêng
    String dosage;
    String frequency;
    String duration;
}
