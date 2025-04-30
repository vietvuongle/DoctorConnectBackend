package com.vuong.DoctorConnext.utils;


import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;

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

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime().after(new java.util.Date());
        } catch (ParseException e) {
            return false;
        }
    }
}

