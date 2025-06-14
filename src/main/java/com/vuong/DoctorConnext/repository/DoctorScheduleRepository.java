package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, String> {
    List<DoctorSchedule> findByDoctorIdAndSlotDateAndIsBookedFalse(String doctorId, LocalDate slotDate);

    List<DoctorSchedule> findByDoctorIdAndSlotDate(String doctorId, LocalDate slotDate);

}
