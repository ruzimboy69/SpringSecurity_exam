package com.example.springsecurity_exam.controller;

import com.example.springsecurity_exam.dto.LoginDto;
import com.example.springsecurity_exam.security.JwtProvider;
import com.example.springsecurity_exam.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        String token=jwtProvider.generateToken(loginDto.getUsername());
        return ResponseEntity.ok().body(token);
    }

}
