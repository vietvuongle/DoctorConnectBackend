package com.vuong.DoctorConnext.dto.response.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentResponse {
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
    String clinicId;
    boolean confirm;
    boolean cancelled;
    boolean payment;
    boolean completed;
    boolean review;
    String dateBooking;
}

