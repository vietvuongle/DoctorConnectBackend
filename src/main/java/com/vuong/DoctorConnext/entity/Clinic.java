package com.vuong.DoctorConnext.entity;

import com.vuong.DoctorConnext.enums.ClinicStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String email;
    String password;
    String image;
    String address;
    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    ClinicStatus status;
}
