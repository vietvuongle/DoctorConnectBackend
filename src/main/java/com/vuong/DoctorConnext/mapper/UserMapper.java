package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.UserCreationRequest;
import com.vuong.DoctorConnext.dto.request.UserUpdateRequest;
import com.vuong.DoctorConnext.dto.response.UserResponse;
import com.vuong.DoctorConnext.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "image", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
