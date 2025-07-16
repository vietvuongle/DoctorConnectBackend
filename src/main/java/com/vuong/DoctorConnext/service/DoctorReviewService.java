package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.dto.request.doctorReview.DoctorReviewCreationRequest;
import com.vuong.DoctorConnext.dto.response.DoctorReviewResponse.DoctorReviewResponse;
import com.vuong.DoctorConnext.dto.response.appointment.AppointmentResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.entity.DoctorReview;
import com.vuong.DoctorConnext.mapper.DoctorReviewMapper;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import com.vuong.DoctorConnext.repository.DoctorReviewRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DoctorReviewService {

    DoctorReviewMapper doctorReviewMapper;
    DoctorReviewRepository doctorReviewRepository;

    AppointmentRepository appointmentRepository;

    public DoctorReview createDoctorReview(DoctorReviewCreationRequest request) {

        // 1. Tìm lịch hẹn
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        // 2. Kiểm tra đã đánh giá chưa
        if (appointment.isReview()) {
            throw new RuntimeException("Lịch hẹn này đã được đánh giá");
        }

        // 3. Đánh dấu đã đánh giá
        appointment.setReview(true);
        appointmentRepository.save(appointment);

        // 4. Chuyển DTO thành Entity DoctorReview
        DoctorReview doctorReview = doctorReviewMapper.toDoctorReview(request);

        // 5. Set ngày tạo
        doctorReview.setCreateAt(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // 6. Lưu đánh giá
        return doctorReviewRepository.save(doctorReview);
    }


    public List<DoctorReviewResponse> getAllDoctorReviewByDoctorId( String doctorId ) {

        // Lấy danh sách lịch hẹn theo bác sĩ
        List<DoctorReview> doctorReviews = doctorReviewRepository.findByDoctorId(doctorId);


        return doctorReviewMapper.toDoctorReviewResponseList(doctorReviews);
    }

    //get top 3 review
    public List<DoctorReview> getTop3HighestRatedReviews() {
        return doctorReviewRepository.findTop3ByOrderByRatingDescCreateAtDesc();
    }
}
