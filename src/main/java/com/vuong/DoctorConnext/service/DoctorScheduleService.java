package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.shedule.ScheduleCreationRequest;
import com.vuong.DoctorConnext.dto.request.shedule.ScheduleSlotDTO;
import com.vuong.DoctorConnext.entity.DoctorSchedule;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;

    public List<DoctorSchedule> createBatchSlots(ScheduleCreationRequest request) {
        List<DoctorSchedule> schedules = request.getSlots().stream().map(dto -> {
            DoctorSchedule schedule = new DoctorSchedule();
            schedule.setDoctorId(request.getDoctorId());
            schedule.setSlotDate(request.getSlotDate());
            schedule.setStartTime(dto.getStartTime());
            schedule.setEndTime(dto.getEndTime());
            schedule.setBooked(false);
            return schedule;
        }).collect(Collectors.toList());

        return scheduleRepository.saveAll(schedules);
    }

    public List<DoctorSchedule> getAvailableSlots(String doctorId, LocalDate slotDate) {
        return scheduleRepository.findByDoctorIdAndSlotDateAndIsBookedFalse(doctorId, slotDate);
    }

    public void cancelBatchSlots(String scheduleId) {

        try {
            scheduleRepository.deleteById(scheduleId);// Xóa department
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);  // Nếu có lỗi khi xóa
        }


    }

}
