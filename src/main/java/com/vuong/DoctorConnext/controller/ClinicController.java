package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.clinic.ClinicResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.service.AuthenticationService;
import com.vuong.DoctorConnext.service.ClinicService;
import com.vuong.DoctorConnext.service.DoctorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClinicController {
    ClinicService clinicService;

    DoctorService doctorService;

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticateDoctor(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticateClinic(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/all-doctor")
    ApiResponse<List<DoctorResponse>> getDoctor() {
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(clinicService.getDoctorByClinicId())
                .build();
    }

    @GetMapping("/all-doctor/{clinicId}")
    ApiResponse<List<DoctorResponse>> getDoctor(@PathVariable String clinicId) {
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(clinicService.getDoctorByClinicId(clinicId))
                .build();
    }

    @GetMapping("/appointments")
    public ApiResponse<List<AppointmentResponse>> getAppointmentsByClinic() {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(clinicService.getAppointmentsByClinicId());

        log.info("data {}", apiResponse);

        return apiResponse;
    }

    @GetMapping("/getClinic")
    public ApiResponse<ClinicResponse> getClinicById(){
        ApiResponse<ClinicResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(clinicService.getClinicById());

        return apiResponse;
    }
}
