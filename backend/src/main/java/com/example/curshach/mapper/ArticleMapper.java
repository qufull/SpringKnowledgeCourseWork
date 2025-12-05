package com.example.curshach.mapper;

import com.example.curshach.dto.articles.ArticleCardGetRequest;
import com.example.curshach.dto.articles.ArticleDto;
import com.example.curshach.dto.articles.ArticleUpdateGetResponse;
import com.example.curshach.model.Article;
import com.example.curshach.model.Tag;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {CommentMapper.class})
public interface ArticleMapper {
    @Mapping(target = "name", source = "author.name")
    ArticleDto toDTO(Article article);

    List<ArticleDto> toDTO(List<Article> article);

    @Mapping(target = "name", source = "author.name")
    @Mapping(source = "article.comments",target = "comments")
    ArticleCardGetRequest toArticleCardGetRequest(Article article);

    @Mapping(target = "categoryId", source = "category.id")
    ArticleUpdateGetResponse toArticleUpdateGetResponse(Article article);

    @AfterMapping
    default void setTags(Article article, @MappingTarget ArticleUpdateGetResponse articleUpdateGetResponse){
        if(article.getTags() != null){
            articleUpdateGetResponse.setTagIds(
                    article.getTags().stream()
                            .map(Tag::getId)
                            .collect(Collectors.toSet())
            );
        }
    }
}
