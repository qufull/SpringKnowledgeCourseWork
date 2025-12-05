package com.example.curshach.controller;

import com.example.curshach.dto.CommentDto;
import com.example.curshach.dto.CommentRequest;
import com.example.curshach.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable Long articleId) {
        return commentService.getByArticle(articleId);
    }

    @PostMapping
    public CommentDto addComment(
            @PathVariable Long articleId,
            @RequestBody CommentRequest request) {
        return commentService.addComment(articleId, request);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
