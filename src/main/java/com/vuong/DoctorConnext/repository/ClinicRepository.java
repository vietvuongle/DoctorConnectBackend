package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, String> {
    Optional<Clinic> findByEmail(String email);

}
