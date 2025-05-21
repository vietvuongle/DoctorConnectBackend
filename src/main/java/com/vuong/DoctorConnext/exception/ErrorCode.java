package com.vuong.DoctorConnext.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên người dùng phải lớn hơn 3 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu phải lớn hơn 8 kí tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Sai email hoặc mật khẩu!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    DEPARTMENT_EXISTED(1008, "Department existed", HttpStatus.BAD_REQUEST),
    DEPARTMENT_NOT_FOUND(1009, "Department not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(1010, "Can not delete", HttpStatus.BAD_REQUEST),
    INVALID_NAME(1011, "Tên người dùng không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_NAME1(1012, "Tên người dùng phải từ 3 đến 20 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_NAME2(1013, "Tên người dùng chỉ được chứa chữ cái và số", HttpStatus.BAD_REQUEST)

    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }


}
