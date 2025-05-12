package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.service.AuthenticationService;
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

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticateDoctor(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticateDoctor(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
