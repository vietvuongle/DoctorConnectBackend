package com.vuong.DoctorConnext.mapper;

import com.vuong.DoctorConnext.dto.request.user.UserCreationRequest;
import com.vuong.DoctorConnext.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegisMapper {
    @Mapping(target = "_id", ignore = true)
    @Mapping(target = "password", ignore = true) // Password sẽ được xử lý riêng
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
}
