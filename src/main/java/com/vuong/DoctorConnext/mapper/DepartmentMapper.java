package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.DepartmentCreationRequest;
import com.vuong.DoctorConnext.dto.response.DepartmentResponse;
import com.vuong.DoctorConnext.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "iconImage", ignore = true)
    Department toDepartment(DepartmentCreationRequest request);

    DepartmentResponse toDepartmentResponse(Department department);
}
