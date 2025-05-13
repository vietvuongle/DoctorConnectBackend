package com.vuong.DoctorConnext.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String _id;

    String userId;
    String doctorId;
    String slotDate;
    String slotTime;
    String reason;
    Long price;
    String patientName;
    String email;
    String phone;
    String dob;
    String gender;
    boolean cancelled;
    boolean payment;
    boolean isConfirm;
    boolean isCompleted;
    String dateBooking;
}
