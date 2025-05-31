package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.user.UserCreationRequest;
import com.vuong.DoctorConnext.dto.request.user.UserUpdateRequest;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Mapping(target = "_id", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "image", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
