package com.example.curshach.repository;

import com.example.curshach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // Поиск пользователя по логину
    Optional<User> findByUsername(String username);

    // Проверка существования логина
    boolean existsByUsername(String username);
}
