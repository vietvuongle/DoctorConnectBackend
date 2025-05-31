package com.vuong.DoctorConnext.dto.request.doctorReview;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorReviewCreationRequest {
    String userId;
    String doctorId;
    int rating;
    String comment;
    String createAt;
    String appointmentId;
}
