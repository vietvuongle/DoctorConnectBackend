package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByName(String name);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
