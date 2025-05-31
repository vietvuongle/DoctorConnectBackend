package com.vuong.DoctorConnext.dto.response.DoctorReviewResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorReviewResponse {
    String id;

    String userId;
    String doctorId;
    int rating;
    String comment;
    String createAt;
}
