package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.request.department.DepartmentCreationRequest;
import com.vuong.DoctorConnext.dto.request.department.DepartmentUpdateRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorCreationRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorUpdateRequest;
import com.vuong.DoctorConnext.dto.request.user.UserUpdateRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.dto.response.department.DepartmentResponse;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.entity.Department;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.service.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5176")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    @Autowired
    DoctorService doctorService;
    DepartmentService departmentService;
    AppointmentService appointmentService;

    AuthenticationService authenticationService;


    @PostMapping("/add-doctor")
    ApiResponse<Doctor> addDoctor(@ModelAttribute  @Valid DoctorCreationRequest request) {
        ApiResponse<Doctor> apiResponse = new ApiResponse<>();
        apiResponse.setResult(doctorService.createDoctor(request));

        return apiResponse;
    }

    @PostMapping("/add-department")
    ApiResponse<Department> addDepartment(@ModelAttribute @Valid DepartmentCreationRequest request) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();
        apiResponse.setResult(departmentService.createDepartment(request));

        return  apiResponse;
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> loginAdmin(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.adminLogin(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/all-department")
    ApiResponse<List<DepartmentResponse>> getDepartment() {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(departmentService.getDepartments())
                .build();
    }

    @GetMapping("/all-doctor")
    ApiResponse<List<DoctorResponse>> getDoctor() {
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getDoctor())
                .build();
    }


    @GetMapping("/all-appointment")
    ApiResponse<List<AppointmentResponse>> getAppointments() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .result(appointmentService.getAppointments())
                .build();
    }

    @PostMapping("/update-department")
    public ResponseEntity<DepartmentResponse> updateDepartment(@ModelAttribute DepartmentUpdateRequest request) {
        DepartmentResponse updateDepartment = departmentService.updateDepartment(request);
        return ResponseEntity.ok(updateDepartment);
    }

    @PostMapping("/update-doctor")
    public ResponseEntity<DoctorResponse> updateDoctor(@ModelAttribute DoctorUpdateRequest request) {
        DoctorResponse updateDoctor = doctorService.updateDoctor(request);

        return ResponseEntity.ok(updateDoctor);
    }

    @DeleteMapping("/delete-department/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String departmentId) {
        try {
            departmentService.deleteDepartment(departmentId);  // Gọi service để xóa
            return ResponseEntity.ok("Xóa khoa thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Xóa khoa thất bại: " + e.getMessage());
        }
    }



}
