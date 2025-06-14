package com.vuong.DoctorConnext.dto.request.shedule;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleCreationRequest {
    private String doctorId;
    private LocalDate slotDate;
    private List<ScheduleSlotDTO> slots;
}
