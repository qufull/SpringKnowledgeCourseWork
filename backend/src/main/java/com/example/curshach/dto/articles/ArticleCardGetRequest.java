package com.example.curshach.dto.articles;


import com.example.curshach.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCardGetRequest {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private String name;
    private List<CommentDto> comments;

}
