package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.appointment.AppointmentCreationRequest;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "_id", ignore = true)
    @Mapping(target = "dateBooking", ignore = true)
    @Mapping(target = "cancelled", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "isCompleted", ignore = true)
    @Mapping(target = "isConfirm", ignore = true)
    Appointment toAppointmentMapper(AppointmentCreationRequest request);

    AppointmentResponse toAppointmentResponse(Appointment appointment);


    List<AppointmentResponse> toAppointmentResponseList(List<Appointment> appointments);


}
