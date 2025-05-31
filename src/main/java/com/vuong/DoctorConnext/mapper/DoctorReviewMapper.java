package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.doctorReview.DoctorReviewCreationRequest;
import com.vuong.DoctorConnext.dto.response.DoctorReviewResponse.DoctorReviewResponse;
import com.vuong.DoctorConnext.entity.DoctorReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    DoctorReview toDoctorReview(DoctorReviewCreationRequest request);

    List<DoctorReviewResponse> toDoctorReviewResponseList(List<DoctorReview> doctorReviews);

}
