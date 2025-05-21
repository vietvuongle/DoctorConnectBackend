package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.MedicalRecordWithPrescriptionRequest;
import com.vuong.DoctorConnext.dto.request.PrescriptionRequest;
import com.vuong.DoctorConnext.dto.response.MedicalRecordWithPrescriptionResponse;
import com.vuong.DoctorConnext.entity.MedicalRecord;
import com.vuong.DoctorConnext.entity.Prescription;
import com.vuong.DoctorConnext.repository.MedicalRecordRepository;
import com.vuong.DoctorConnext.repository.PrescriptionRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicalRecordService {
    PrescriptionRepository prescriptionRepository;
    MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord createMedicalRecord(MedicalRecordWithPrescriptionRequest dto) {
        MedicalRecord record = MedicalRecord.builder()
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .symptoms(dto.getSymptoms())
                .diagnosis(dto.getDiagnosis())
                .examination(dto.getExamination())
                .treatment(dto.getTreatment())
                .notes(dto.getNotes())
                .createdAt(LocalDateTime.now().toString())
                .build();

        MedicalRecord savedRecord = medicalRecordRepository.save(record);

        log.info("fullData {}", dto);
        log.info("prescription {}", dto.getPrescriptions());

        for (PrescriptionRequest prescriptionDto : dto.getPrescriptions()) {
            Prescription p = Prescription.builder()
                    .medicalRecordId(savedRecord.getId())
                    .medicineName(prescriptionDto.getMedicine())
                    .dosage(prescriptionDto.getDosage())
                    .frequency(prescriptionDto.getFrequency())
                    .duration(prescriptionDto.getDuration())
                    .build();
            prescriptionRepository.save(p);
        }

        return savedRecord;
    }

    public List<MedicalRecordWithPrescriptionResponse> getMedicalRecordsWithPrescriptions(String patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);

        return records.stream().map(record -> {
            List<Prescription> prescriptions = prescriptionRepository.findByMedicalRecordId(record.getId());
            return new MedicalRecordWithPrescriptionResponse(record, prescriptions);
        }).collect(Collectors.toList());
    }


    public Optional<MedicalRecord> getRecordById(String id) {
        return medicalRecordRepository.findById(id);
    }

    public List<MedicalRecord> getRecordsByPatientId(String patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    public void deleteMedicalRecord(String id) {
        medicalRecordRepository.deleteById(id);
    }
}
