package com.example.curshach.controller;

import com.example.curshach.dto.auth.AuthResponse;
import com.example.curshach.dto.auth.LoginRequest;
import com.example.curshach.dto.auth.RegisterDto;
import com.example.curshach.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest login){
        return authService.login(login);
    }


    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterDto reg){
        return authService.register(reg);
    }


}
