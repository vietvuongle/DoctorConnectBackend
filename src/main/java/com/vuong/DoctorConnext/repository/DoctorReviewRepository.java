package com.vuong.DoctorConnext.repository;

import com.vuong.DoctorConnext.entity.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorReviewRepository extends JpaRepository<DoctorReview, String> {
    List<DoctorReview> findByDoctorId(String doctorId);

    List<DoctorReview> findAllByOrderByRatingDescCreateAtDesc();


}
