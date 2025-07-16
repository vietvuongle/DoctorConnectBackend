package com.vuong.DoctorConnext.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.vuong.DoctorConnext.dto.request.AuthenticationRequest;
import com.vuong.DoctorConnext.dto.response.AuthenticationResponse;
import com.vuong.DoctorConnext.entity.Clinic;
import com.vuong.DoctorConnext.entity.Doctor;
import com.vuong.DoctorConnext.entity.User;
import com.vuong.DoctorConnext.exception.AppException;
import com.vuong.DoctorConnext.exception.ErrorCode;
import com.vuong.DoctorConnext.repository.ClinicRepository;
import com.vuong.DoctorConnext.repository.DoctorRepository;
import com.vuong.DoctorConnext.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    DoctorRepository doctorRepository;
    ClinicRepository clinicRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;



    public AuthenticationResponse authenticateUser(AuthenticationRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateTokenUser(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse authenticateDoctor(AuthenticationRequest request){
        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), doctor.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateTokenDoctor(doctor);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse authenticateClinic(AuthenticationRequest request){
        Clinic clinic = clinicRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), clinic.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateTokenClinic(clinic);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse adminLogin(AuthenticationRequest request){
        String token = null;
        if (request.getEmail().equals("admin@gmail.com") && request.getPassword().equals("12345678")) {
            token = generateToken();
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }


        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public User loadOrCreateUserFromOAuth2(String email, String name) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setPassword(""); // Có thể bỏ qua password vì Google đã xác thực
                    user.setRoles(Set.of("USER")); // Gán quyền mặc định
                    return userRepository.save(user);
                });
    }



    public String generateTokenUser(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .issuer("vuong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScopeUser(user))
                .claim("userId", user.get_id())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String generateTokenDoctor(Doctor doctor) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(doctor.getName())
                .issuer("vuong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScopeDoctor(doctor))
                .claim("doctorId", doctor.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String generateTokenClinic(Clinic clinic) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(clinic.getName())
                .issuer("vuong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScopeClinic())
                .claim("clinicId", clinic.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String generateToken() {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("vuong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", Arrays.asList("ADMIN", "USER", "DOCTOR"))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }


    private String[] buildScopeUser(User user) {
        List<String> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            roles.addAll(user.getRoles());
        }
        return roles.toArray(new String[0]);
    }


    private String[] buildScopeDoctor(Doctor doctor) {
        List<String> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(doctor.getRoles()))
            roles.addAll(doctor.getRoles());

        return roles.toArray(new String[0]);
    }

    private String[] buildScopeClinic() {
        List<String> roles = new ArrayList<>();
            roles.add("CLINIC");

        return roles.toArray(new String[0]);
    }


}