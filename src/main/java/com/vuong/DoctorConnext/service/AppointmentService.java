package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.appointment.AppointmentCreationRequest;

import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.department.DepartmentResponse;

import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.mapper.AppointmentMapper;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    AppointmentMapper appointmentMapper;


    public Appointment createAppointment(AppointmentCreationRequest request) {

        Appointment appointment = appointmentMapper.toAppointmentMapper(request);

        log.info("appointment {}", appointment);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = LocalDate.now().format(formatter);

        appointment.setDateBooking(today);

        return appointmentRepository.save(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AppointmentResponse> getAppointments() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toAppointmentResponse).toList();
    }
}

