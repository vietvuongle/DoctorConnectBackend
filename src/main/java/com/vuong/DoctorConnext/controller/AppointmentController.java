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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5175")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    UserService userService;

    JWTUtils jwtUtils;

//        @PostMapping
//        public ApiResponse<Appointment> createAppointment(@RequestBody @Valid AppointmentCreationRequest request) {
//            ApiResponse<Appointment> response = new ApiResponse<>();
//            response.setResult(appointmentService.createAppointment(request));
//            return response;
//        }

    @PostMapping
    public ApiResponse<Appointment> createAppointment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid AppointmentCreationRequest request) {

        // Lấy token từ header, thường dạng "Bearer ey...."
        String token = authHeader.replace("Bearer ", "");
        // Extract userId từ token
        String userId = jwtUtils.extractUserId(token);
        // Gán userId vào request (nếu cần là Long thì parse)
        request.setUserId(userId);

        ApiResponse<Appointment> response = new ApiResponse<>();
        response.setResult(appointmentService.createAppointment(request));
        return response;
    }



    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        List<Appointment> appointments = appointmentService.getMyAppointments();
        // Trả về mảng trống nếu không có lịch hẹn, không trả chuỗi.
        return ResponseEntity.ok(appointments);
    }

}
