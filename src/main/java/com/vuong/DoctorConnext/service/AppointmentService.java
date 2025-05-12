package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.appointment.AppointmentCreationRequest;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.mapper.AppointmentMapper;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = LocalDate.now().format(formatter);

        appointment.setDateBooking(today);

        return appointmentRepository.save(appointment);
    }
}
