package com.vuong.DoctorConnext.utils;


import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtils {

    public String extractUserId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getStringClaim("userId");
        } catch (ParseException e) {
            throw new RuntimeException("Cannot extract userId from token", e);
        }
    }

    public String extractDoctorId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getStringClaim("doctorId");
        } catch (ParseException e) {
            throw new RuntimeException("Cannot extract doctorId from token", e);
        }
    }

    public String extractClinicId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getStringClaim("clinicId");
        } catch (ParseException e) {
            throw new RuntimeException("Cannot extract clinicId from token", e);
        }
    }


    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expiration != null && expiration.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        try {
            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Get the "scope" claim
            Object scopeClaim = claims.getClaim("scope");

            // Kiểm tra xem "scope" có phải là List hay không
            if (scopeClaim instanceof List) {
                return (List<String>) scopeClaim;  // Nếu là List thì trả về trực tiếp
            }

            // Nếu "scope" là một chuỗi, chia nó thành List
            if (scopeClaim instanceof String) {
                return Arrays.asList(((String) scopeClaim).split(" ")); // Giả sử các roles được phân cách bằng dấu cách
            }

            // Trả về danh sách rỗng nếu không phải kiểu dữ liệu mong đợi
            return List.of();

        } catch (ParseException e) {
            throw new RuntimeException("Cannot extract roles from token", e);
        }
    }

}

