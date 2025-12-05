package com.example.curshach.repository;

import com.example.curshach.model.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    // Все комментарии по статье
    @EntityGraph(attributePaths = "user")
    List<Comment> findByArticleId(Long articleId);


}
