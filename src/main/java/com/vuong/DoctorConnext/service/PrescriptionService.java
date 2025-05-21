package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.entity.Prescription;
import com.vuong.DoctorConnext.repository.PrescriptionRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrescriptionService {

    PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Optional<Prescription> getPrescriptionById(String id) {
        return prescriptionRepository.findById(id);
    }

    public List<Prescription> getPrescriptionsByMedicalRecordId(String medicalRecordId) {
        return prescriptionRepository.findByMedicalRecordId(medicalRecordId);
    }

    public void deletePrescription(String id) {
        prescriptionRepository.deleteById(id);
    }
}
