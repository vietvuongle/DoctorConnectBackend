package com.vuong.DoctorConnext.entity;

import com.vuong.DoctorConnext.enums.ClinicStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "clinic_images", joinColumns = @JoinColumn(name = "clinic_id"))
    @Column(name = "image_url")
    List<String> images = new ArrayList<>(); // Danh sách ảnh phụ

    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    ClinicStatus status;
}
