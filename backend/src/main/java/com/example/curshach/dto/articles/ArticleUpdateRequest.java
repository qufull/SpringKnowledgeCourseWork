package com.example.curshach.dto.articles;

import lombok.Data;

import java.util.Set;

@Data
public class ArticleUpdateRequest {
    private String title;
    private String content;
    private Long categoryId;
    private Set<Long> tagIds;
}
