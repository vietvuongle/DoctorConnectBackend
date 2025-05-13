package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorCreationRequest;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.enums.Role;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.mapper.DoctorMapper;
import com.vuong.DoctorConnext.repository.DoctorRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {
    DoctorRepository doctorRepository;
    DoctorMapper doctorMapper;
    CloudinaryService cloudinaryService;
    PasswordEncoder passwordEncoder;

    public Doctor createDoctor(DoctorCreationRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        Doctor doctor = doctorMapper.toDoctor(request);

        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.DOCTOR.name());
        doctor.setRoles(roles);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                doctor.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return doctorRepository.save(doctor);
    }

//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('USER')")
    public List<DoctorResponse> getDoctor() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDoctorResponse).toList();
    }

    public DoctorResponse getDoctorById(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return doctorMapper.toDoctorResponse(doctor);
    }

    public DoctorResponse getDoctorById() {
        String doctorId = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();  // Lấy doctorId từ details

        // Tìm người dùng theo userId
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return doctorMapper.toDoctorResponse(doctor);
    }
}
