package com.vuong.DoctorConnext.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    String image;
    String experience;
    String fees;
    String speciality;
    String about;
    String degree;
    String sex;
    String phone;
    String address;
    Set<String> roles;
}
