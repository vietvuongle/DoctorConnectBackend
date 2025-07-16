package com.vuong.DoctorConnext.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails {
    private String doctorId;
    private String clinicId;
}
