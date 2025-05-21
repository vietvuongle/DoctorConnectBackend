package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    List<Prescription> findByMedicalRecordId(String medicalRecordId);
}
