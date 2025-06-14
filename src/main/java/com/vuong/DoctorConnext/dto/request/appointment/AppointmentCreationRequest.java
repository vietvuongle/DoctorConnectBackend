package com.vuong.DoctorConnext.dto.request.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentCreationRequest {
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
    boolean isConfirm;

    boolean cancelled;
    boolean payment;
    boolean isCompleted;
    boolean isReview;

    String dateBooking;

}

