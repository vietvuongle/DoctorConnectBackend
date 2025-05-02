package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.request.DoctorCreationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.service.AuthenticationService;
import com.vuong.DoctorConnext.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    @Autowired
    DoctorService doctorService;

    AuthenticationService authenticationService;


    @PostMapping("/add-doctor")
    ApiResponse<Doctor> addDoctor(@ModelAttribute  @Valid DoctorCreationRequest request) {
        ApiResponse<Doctor> apiResponse = new ApiResponse<>();
        apiResponse.setResult(doctorService.createDoctor(request));

        return apiResponse;
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> loginAdmin(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.adminLogin(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

}
