package com.example.curshach.controller;

import com.example.curshach.dto.UserDto;
import com.example.curshach.service.ArticleService;
import com.example.curshach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {
    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping
    public UserDto getProfile(Principal principal) {
        return userService.getProfile(principal.getName());
    }
}
