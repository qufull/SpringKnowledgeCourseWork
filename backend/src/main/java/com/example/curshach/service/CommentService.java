package com.example.curshach.service;

import com.example.curshach.dto.CommentDto;
import com.example.curshach.dto.CommentRequest;
import com.example.curshach.mapper.CommentMapper;
import com.example.curshach.model.Article;
import com.example.curshach.model.Comment;
import com.example.curshach.model.User;
import com.example.curshach.repository.ArticleRepository;
import com.example.curshach.repository.CommentRepository;
import com.example.curshach.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    public List<CommentDto> getByArticle(Long articleId) {
        return  mapper.toDTO(commentRepository.findByArticleId(articleId));
    }

    public CommentDto addComment(Long articleId, CommentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Comment comment = Comment.builder()
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .user(user)
                .article(article)
                .build();

        return mapper.toDTO(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
