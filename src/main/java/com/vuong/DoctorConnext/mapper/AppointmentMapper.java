package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.appointment.AppointmentCreationRequest;
import com.vuong.DoctorConnext.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "_id", ignore = true)
    @Mapping(target = "dateBooking", ignore = true)
    @Mapping(target = "cancelled", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "isCompleted", ignore = true)
    Appointment toAppointmentMapper(AppointmentCreationRequest request);
}
