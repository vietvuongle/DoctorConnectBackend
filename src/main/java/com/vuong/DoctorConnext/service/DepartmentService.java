package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.DepartmentCreationRequest;
import com.vuong.DoctorConnext.dto.response.DepartmentResponse;
import com.vuong.DoctorConnext.entity.Department;
import com.vuong.DoctorConnext.enums.Role;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.mapper.DepartmentMapper;
import com.vuong.DoctorConnext.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;
    CloudinaryService cloudinaryService;

    public Department createDepartment(DepartmentCreationRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
        }

        Department department = departmentMapper.toDepartment(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.ADMIN.name());
        department.setRoles(roles);

        if(request.getIconImage() != null && !request.getIconImage().isEmpty()) {
            try {
                String urlIconImage = cloudinaryService.getImageUrlAfterUpload(request.getIconImage());
                department.setIconImage(urlIconImage);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return departmentRepository.save(department);
    }

    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toDepartmentResponse).toList();
    }
}
