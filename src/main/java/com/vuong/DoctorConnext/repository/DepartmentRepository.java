package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByName(String name);

}
