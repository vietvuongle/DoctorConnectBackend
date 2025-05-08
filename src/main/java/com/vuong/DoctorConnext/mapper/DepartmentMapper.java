package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.department.DepartmentCreationRequest;
import com.vuong.DoctorConnext.dto.request.department.DepartmentUpdateRequest;
import com.vuong.DoctorConnext.dto.response.department.DepartmentResponse;
import com.vuong.DoctorConnext.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "iconImage", ignore = true)
    Department toDepartment(DepartmentCreationRequest request);

    DepartmentResponse toDepartmentResponse(Department department);

    @Mapping(target = "iconImage", ignore = true)
    void updateDepartment(@MappingTarget Department department, DepartmentUpdateRequest request);
}
