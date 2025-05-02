package com.vuong.DoctorConnext.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vuong.DoctorConnext.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
     int code = 1000;
     String message;
     T result;


     public static <T> ApiResponse<T> error(ErrorCode errorCode) {
          ApiResponse<T> response = new ApiResponse<>();
          response.setCode(errorCode.getCode());
          response.setMessage(errorCode.getMessage());
          return response;
     }
}
