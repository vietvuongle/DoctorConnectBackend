package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorCreationRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorUpdateRequest;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.enums.Role;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.mapper.AppointmentMapper;
import com.vuong.DoctorConnext.mapper.DoctorMapper;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
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
import java.util.HashSet;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {
    DoctorRepository doctorRepository;
    AppointmentRepository appointmentRepository;

    AppointmentMapper appointmentMapper;
    DoctorMapper doctorMapper;
    CloudinaryService cloudinaryService;
    PasswordEncoder passwordEncoder;

    public Doctor createDoctor(DoctorCreationRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        Doctor doctor = doctorMapper.toDoctor(request);

        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.DOCTOR.name());
        doctor.setRoles(roles);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                doctor.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return doctorRepository.save(doctor);
    }

//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('USER')")
    public List<DoctorResponse> getDoctor() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDoctorResponse).toList();
    }

    public DoctorResponse getDoctorById(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return doctorMapper.toDoctorResponse(doctor);
    }

    public DoctorResponse getDoctorById() {
        String doctorId = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();  // Lấy doctorId từ details

        // Tìm người dùng theo userId
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return doctorMapper.toDoctorResponse(doctor);
    }

    public List<AppointmentResponse> getAppointmentsByDoctorId() {
        String doctorId = (String) SecurityContextHolder.getContext().getAuthentication().getDetails(); // Hoặc getPrincipal nếu bạn lưu ID trong đó

        // Lấy danh sách lịch hẹn theo bác sĩ
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        // Nếu không có lịch hẹn
        if (appointments.isEmpty()) {
            throw new RuntimeException("No appointments found for this doctor");
        }

        return appointmentMapper.toAppointmentResponseList(appointments);
    }

    public AppointmentResponse confirmAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        appointment.setConfirm(true);

        return appointmentMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));
        appointment.setCancelled(true);

        return appointmentMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse completeAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));
        appointment.setCompleted(true);

        return appointmentMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    public DoctorResponse updateDoctor(DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        doctorMapper.updateDoctor(doctor, request);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                doctor.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }
}
