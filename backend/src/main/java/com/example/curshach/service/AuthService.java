package com.example.curshach.service;

import com.example.curshach.dto.auth.AuthResponse;
import com.example.curshach.dto.auth.LoginRequest;
import com.example.curshach.dto.auth.RegisterDto;
import com.example.curshach.enums.Role;
import com.example.curshach.model.Author;
import com.example.curshach.model.User;
import com.example.curshach.repository.AuthorRepository;
import com.example.curshach.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(@RequestBody RegisterDto request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь уже существует");
        }

        // Создаём пользователя
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EDITOR)
                .build();

        userRepository.save(user);

        Author author = new Author();
        author.setName(request.getUsername());
        author.setEmail("");
        author.setBio("");
        author.setUser(user);

        authorRepository.save(author);

        // Генерация токена
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .username(user.getUsername())
                .role(user.getRole().name())
                .userId(user.getId())
                .build();


    }

    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow((() -> new EntityNotFoundException("Пользователь не найден")));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .username(user.getUsername())
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }
}
