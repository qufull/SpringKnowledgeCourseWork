package com.example.curshach.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String role;
    private String username;
    private Long userId;
}
