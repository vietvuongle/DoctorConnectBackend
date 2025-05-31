package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.request.password.PasswordChangeRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.service.AppointmentService;
import com.vuong.DoctorConnext.service.AuthenticationService;
import com.vuong.DoctorConnext.service.DoctorService;
import com.vuong.DoctorConnext.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    AuthenticationService authenticationService;
    DoctorService doctorService;


    UserService userService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticateDoctor(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticateDoctor(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{doctorId}")
    public ApiResponse<DoctorResponse> getDoctorById(@PathVariable("doctorId") String doctorId) {
        ApiResponse<DoctorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(doctorService.getDoctorById(doctorId));

        return apiResponse;
    }


    @GetMapping("/getUser/{userId}")
    public ApiResponse<UserResponse> getUserByUserId(@PathVariable("userId") String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserByUserId(userId));

        return apiResponse;
    }


    @GetMapping("/getDoctor")
    public ApiResponse<DoctorResponse> getDoctorById(){
        ApiResponse<DoctorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(doctorService.getDoctorById());

        return apiResponse;
    }


    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctor() {
        List<AppointmentResponse> appointments = doctorService.getAppointmentsByDoctorId();
        return ResponseEntity.ok(appointments);
    }



    @PutMapping("/appointment/confirm/{id}")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable String id) {
        AppointmentResponse response = doctorService.confirmAppointment(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/appointment/cancel/{id}")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable String id) {
        AppointmentResponse response = doctorService.cancelAppointment(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/appointment/completed/{id}")
    public ResponseEntity<AppointmentResponse> completeAppointment(@PathVariable String id) {
        AppointmentResponse response = doctorService.completeAppointment(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        doctorService.changePassword(request.getCurrentPassword(), request.getNewPassword());
        apiResponse.setResult("Password updated successfully");

        return apiResponse;

    }

}

