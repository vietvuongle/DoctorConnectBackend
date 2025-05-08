package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.user.UserCreationRequest;
import com.vuong.DoctorConnext.dto.request.user.UserUpdateRequest;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.entity.User;
import com.vuong.DoctorConnext.enums.Role;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.mapper.UserMapper;
import com.vuong.DoctorConnext.mapper.UserRegisMapper;
import com.vuong.DoctorConnext.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserRegisMapper userRegisMapper;
    UserMapper userMapper;
    CloudinaryService cloudinaryService;

    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) {
        if(userRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userRegisMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }




    public UserResponse updateUser(UserUpdateRequest request) {

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);

        // Xử lý cập nhật ảnh nếu có file được gửi lên
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                user.setImage(imageUrl); // Giả sử entity User có trường avatarUrl
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

}
