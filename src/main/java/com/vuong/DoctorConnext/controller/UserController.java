package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.configuration.CloudinaryService;
import com.vuong.DoctorConnext.dto.request.ApiResponse;
import com.vuong.DoctorConnext.dto.request.user.UserCreationRequest;
import com.vuong.DoctorConnext.dto.request.user.UserUpdateRequest;
import com.vuong.DoctorConnext.dto.response.user.UserResponse;
import com.vuong.DoctorConnext.entity.User;
import com.vuong.DoctorConnext.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5175")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    @Autowired
    private CloudinaryService cloudinaryService;

    UserService userService;

    @PostMapping("/register")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.getImageUrlAfterUpload(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải lên ảnh: " + e.getMessage());
        }
    }

    @PutMapping("/get-profile")
    public ResponseEntity<UserResponse> updateUser(@ModelAttribute UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(request);
        return ResponseEntity.ok(updatedUser);
    }


}
