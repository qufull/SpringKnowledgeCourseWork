package com.example.curshach.repository;

import com.example.curshach.model.Author;
import com.example.curshach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    // Поиск по имени автора
    Optional<Author> findByUser(User user);
}
