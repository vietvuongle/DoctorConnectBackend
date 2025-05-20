package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.service.AuthenticationService;
import com.vuong.DoctorConnext.service.DoctorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    AuthenticationService authenticationService;
    DoctorService doctorService;

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

    @GetMapping("/getDoctor")
    public ApiResponse<DoctorResponse> getDoctorById(){
        ApiResponse<DoctorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(doctorService.getDoctorById());

        return apiResponse;
    }


}