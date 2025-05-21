package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.appointment.AppointmentCreationRequest;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.service.AppointmentService;
import com.vuong.DoctorConnext.service.UserService;
import com.vuong.DoctorConnext.utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5175")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    UserService userService;

    JWTUtils jwtUtils;

    @PostMapping
    public ApiResponse<Appointment> createAppointment(@RequestBody @Valid AppointmentCreationRequest request) {
        ApiResponse<Appointment> response = new ApiResponse<>();
        response.setResult(appointmentService.createAppointment(request));
        return response;
    }




}
