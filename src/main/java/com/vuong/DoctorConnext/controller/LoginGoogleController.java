package com.vuong.DoctorConnext.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginGoogleController {
    @GetMapping
    public String welcome() {
        return "welcome login google";
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("userName: " + principal.getName());

        return principal;
    }
}
