package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.MedicalRecordWithPrescriptionRequest;
import com.vuong.DoctorConnext.dto.request.PrescriptionRequest;
import com.vuong.DoctorConnext.dto.response.MedicalRecordWithPrescriptionResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.entity.MedicalRecord;
import com.vuong.DoctorConnext.entity.Prescription;
import com.vuong.DoctorConnext.entity.User;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import com.vuong.DoctorConnext.repository.MedicalRecordRepository;
import com.vuong.DoctorConnext.repository.PrescriptionRepository;
import com.vuong.DoctorConnext.repository.UserRepository;
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
    MailContactService emailService;

    PrescriptionRepository prescriptionRepository;
    MedicalRecordRepository medicalRecordRepository;
    UserRepository userRepository;
    AppointmentRepository appointmentRepository;

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

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn"));

        log.info("appointment {} ", appointment);

        log.info("email: ", appointment.getEmail());

        String subject = "Bệnh án của bạn đã được tạo";
        String content = String.format(
                "Chào %s,\n\nBệnh án của bạn đã được tạo thành công.\n\nChẩn đoán: %s\nTriệu chứng: %s\nPhác đồ điều trị: %s\n\nTrân trọng.",
                appointment.getPatientName(),
                savedRecord.getDiagnosis(),
                savedRecord.getSymptoms(),
                savedRecord.getTreatment()
        );

        emailService.sendMedicalRecordEmail(appointment.getEmail(), subject, content);

        appointment.setCompleted(true);
        appointmentRepository.save(appointment);

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
