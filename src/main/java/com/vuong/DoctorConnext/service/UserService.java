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

        // Kiểm tra và cập nhật các trường nếu có giá trị
        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }

        if (request.getDob() != null && !request.getDob().isEmpty()) {
            user.setDob(request.getDob());
        }

        if (request.getGender() != null && !request.getGender().isEmpty()) {
            user.setGender(request.getGender());
        }

        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            user.setAddress(request.getAddress());
        }

        // Xử lý ảnh nếu có
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.getImageUrlAfterUpload(request.getImage());
                user.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }

        // Lưu user đã cập nhật
        return userMapper.toUserResponse(userRepository.save(user));
    }


    public UserResponse getUser() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }

    public void changePassword(String currentPassword, String newPassword) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS); // Mật khẩu cũ không chính xác
        }

        // Kiểm tra độ dài của mật khẩu mới (nếu có yêu cầu)
        if (newPassword.length() < 6) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));

        // Lưu thông tin người dùng sau khi thay đổi mật khẩu
        userRepository.save(user);
    }

}
