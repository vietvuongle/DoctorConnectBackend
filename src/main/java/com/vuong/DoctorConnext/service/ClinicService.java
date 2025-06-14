package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.client.CustomUserDetails;
import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.clinic.ClinicCreationRequest;
import com.vuong.DoctorConnext.dto.request.clinic.ClinicUpdateRequest;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.clinic.ClinicResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.entity.Clinic;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.enums.ClinicStatus;
import com.vuong.DoctorConnext.mapper.AppointmentMapper;
import com.vuong.DoctorConnext.mapper.ClinicMapper;
import com.vuong.DoctorConnext.mapper.DoctorMapper;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import com.vuong.DoctorConnext.repository.ClinicRepository;
import com.vuong.DoctorConnext.repository.DoctorRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClinicService {
    ClinicRepository clinicRepository;
    ClinicMapper clinicMapper;

    DoctorRepository doctorRepository;
    DoctorMapper doctorMapper;

    AppointmentRepository appointmentRepository;
    AppointmentMapper appointmentMapper;

    CloudinaryService cloudinaryService;

    PasswordEncoder passwordEncoder;

    public Clinic createClinic(ClinicCreationRequest request) {
        Clinic clinic = clinicMapper.toClinic(request);

        clinic.setPassword(passwordEncoder.encode(request.getPassword()));
        clinic.setStatus(ClinicStatus.APPROVED);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                clinic.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return clinicRepository.save(clinic);
    }

    public ClinicResponse getClinicById() {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String clinicId = details.getClinicId();

        // Tìm người dùng theo userId
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return clinicMapper.toClinicResponse(clinic);
    }

    public List<ClinicResponse> getClinics() {
        return clinicRepository.findAll().stream().map(clinicMapper::toClinicResponse).toList();
    }

    public List<DoctorResponse> getDoctorByClinicId() {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String clinicId = details.getClinicId();

        List<Doctor> doctors = doctorRepository.findByClinicId(clinicId);
        System.out.println("Found doctors: " + doctors.size()); // debug

        return doctors.stream().map(doctorMapper::toDoctorResponse).toList();
    }

    public ClinicResponse updateClinic(ClinicUpdateRequest request) {
        Clinic clinic = clinicRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        clinicMapper.updateClinic(clinic, request);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                clinic.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return clinicMapper.toClinicResponse(clinicRepository.save(clinic));
    }

    public List<AppointmentResponse> getAppointmentsByClinicId() {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String clinicId = details.getClinicId();

        // Lấy danh sách lịch hẹn theo bác sĩ
        List<Appointment> appointments = appointmentRepository.findByClinicId(clinicId);


        return appointmentMapper.toAppointmentResponseList(appointments);
    }
}
