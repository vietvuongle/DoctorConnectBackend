package com.vuong.DoctorConnext.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String email;
    String password;
    String clinicId;
    String image;
    String experience;
    String fees;
    String speciality;
    @Lob
    @Column(columnDefinition = "TEXT")
    String about;
    String degree;
    String sex;
    String phone;
    String address;
    String school;
    Set<String> roles;
}
