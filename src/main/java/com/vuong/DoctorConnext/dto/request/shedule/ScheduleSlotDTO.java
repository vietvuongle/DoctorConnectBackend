package com.vuong.DoctorConnext.dto.request.shedule;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
}
