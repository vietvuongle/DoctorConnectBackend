package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(String userId);
}