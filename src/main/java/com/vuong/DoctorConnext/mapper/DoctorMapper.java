package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.department.DepartmentUpdateRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorCreationRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorUpdateRequest;
import com.vuong.DoctorConnext.dto.response.doctor.DoctorResponse;
import com.vuong.DoctorConnext.entity.Department;
import com.vuong.DoctorConnext.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Password sẽ được xử lý riêng
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "image", ignore = true)
    Doctor toDoctor(DoctorCreationRequest request);
    DoctorResponse toDoctorResponse(Doctor doctor);

    @Mapping(target = "image", ignore = true)
    void updateDoctor(@MappingTarget Doctor doctor, DoctorUpdateRequest request);
}
