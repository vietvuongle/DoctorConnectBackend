package com.vuong.DoctorConnext.configuration;

import com.vuong.DoctorConnext.client.CustomUserDetails;
import com.vuong.DoctorConnext.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");


                if (jwtUtils.validateToken(token)) {
                    String userId = jwtUtils.extractUserId(token);
                    String doctorId = jwtUtils.extractDoctorId(token); // Lấy doctorId từ token
                    String clinicId = jwtUtils.extractClinicId(token);
                    List<String> roles = jwtUtils.extractRoles(token); // Lấy danh sách role từ token

                    // Chuyển đổi role (String) -> GrantedAuthority
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());

                    // Tạo Authentication object
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    authorities
                            );
                    CustomUserDetails customDetails = new CustomUserDetails(doctorId, clinicId);

                    // Lưu doctorId và clinicId vào 'details' của Authentication
                    authenticationToken.setDetails(customDetails);  // Lưu doctorId vào details

                    // Gán vào SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        } catch (Exception ex) {
            log.error("Cannot set user authentication", ex);
        }

        filterChain.doFilter(request, response);
    }
}
