package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.MedicalRecordWithPrescriptionRequest;
import com.vuong.DoctorConnext.dto.response.MedicalRecordWithPrescriptionResponse;
import com.vuong.DoctorConnext.entity.MedicalRecord;
import com.vuong.DoctorConnext.service.MedicalRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/medicalRecords")
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicalRecordController {
    MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecordWithPrescriptionRequest dto) {
        MedicalRecord saved = medicalRecordService.createMedicalRecord(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getRecord/{patientId}")
    public ResponseEntity<List<MedicalRecordWithPrescriptionResponse>> getMyMedicalRecords(@PathVariable String patientId) {

        List<MedicalRecordWithPrescriptionResponse> result = medicalRecordService.getMedicalRecordsWithPrescriptions(patientId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecordById(@PathVariable String id) {
        return medicalRecordService.getRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getRecordsByPatientId(@PathVariable String patientId) {
        return medicalRecordService.getRecordsByPatientId(patientId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
