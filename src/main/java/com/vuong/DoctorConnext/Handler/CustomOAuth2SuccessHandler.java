package com.vuong.DoctorConnext.Handler;


import com.vuong.DoctorConnext.entity.User;
import com.vuong.DoctorConnext.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Kiểm tra user đã tồn tại chưa, nếu chưa thì tạo
        User user = authenticationService.loadOrCreateUserFromOAuth2(email, name);

        // Sinh JWT
        String token = authenticationService.generateTokenUser(user);

        // Trả JWT cho client dưới dạng JSON
        String redirectUrl = "http://localhost:5175/oauth2-success?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
