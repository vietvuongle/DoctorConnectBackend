package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.clinic.ClinicCreationRequest;
import com.vuong.DoctorConnext.dto.request.clinic.ClinicUpdateRequest;
import com.vuong.DoctorConnext.dto.request.doctor.DoctorUpdateRequest;
import com.vuong.DoctorConnext.dto.response.clinic.ClinicResponse;
import com.vuong.DoctorConnext.entity.Clinic;
import com.vuong.DoctorConnext.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "image", ignore = true)
    Clinic toClinic(ClinicCreationRequest request);

    ClinicResponse toClinicResponse(Clinic clinic);

    @Mapping(target = "image", ignore = true)
    void updateClinic(@MappingTarget Clinic clinic, ClinicUpdateRequest request);
}
