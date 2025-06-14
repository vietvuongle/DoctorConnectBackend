package com.vuong.DoctorConnext.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    private String doctorId;

    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isBooked = false;
}
