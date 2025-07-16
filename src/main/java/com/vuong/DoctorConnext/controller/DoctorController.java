package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.request.password.PasswordChangeRequest;
import com.vuong.DoctorConnext.dto.request.shedule.ScheduleCreationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.entity.DoctorSchedule;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.service.*;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    AuthenticationService authenticationService;
    DoctorService doctorService;

    DoctorScheduleService scheduleService;


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

    @PostMapping("/create-schedule")
    public ApiResponse<List<DoctorSchedule>> createBatch(@RequestBody ScheduleCreationRequest request) {
        ApiResponse<List<DoctorSchedule>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(scheduleService.createBatchSlots(request));

        return apiResponse;
    }

    @GetMapping("/available/{doctorId}")
    public ApiResponse<List<DoctorSchedule>> getAvailableSlots(
            @PathVariable String doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate slotDate) {

        List<DoctorSchedule> availableSchedules = scheduleService.getAvailableSlots(doctorId, slotDate);
        log.info("data {} ", availableSchedules);

        ApiResponse<List<DoctorSchedule>> response = new ApiResponse<>();
        response.setResult(availableSchedules);
        response.setMessage("Lấy danh sách lịch trống thành công");

        return response;
    }

    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<?> cancelSchedules(@PathVariable String scheduleId) {
        scheduleService.cancelBatchSlots(scheduleId);
        return ResponseEntity.ok(Map.of(
                "code", 1000,
                "message", "Hủy lịch thành công"
        ));
    }


}

