package com.example.curshach.service;

import com.example.curshach.dto.articles.*;
import com.example.curshach.enums.Role;
import com.example.curshach.mapper.ArticleMapper;
import com.example.curshach.model.*;
import com.example.curshach.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final ArticleMapper mapper;

    public List<ArticleDto> getAll() {
        return mapper.toDTO(articleRepository.findAll());
    }

    public ArticleCardGetRequest getById(Long id) {
        return mapper.toArticleCardGetRequest(
                articleRepository.findFullById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Статья не найдена"))
        );
    }

    public ArticleUpdateGetResponse getByIdWithTagsAndCategory(Long id){
        return mapper.toArticleUpdateGetResponse(
                articleRepository.findWithTagsAndCategoryById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Статья не найдена"))
        );
    }

    public List<ArticleDto> getMyArticles(String username) {
        return mapper.toDTO(articleRepository.findByAuthorUserUsername(username));
    }
    @Transactional
    public ArticleDto create(ArticleCreateRequest req, String username) {

        User current = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Author author = authorRepository.findByUser(current)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(req.getTagIds()));
        
            Article article = Article.builder()
                    .title(req.getTitle())
                    .content(req.getContent())
                    .author(author)
                    .category(category)
                    .tags(tags)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();


        articleRepository.save(article);
        return mapper.toDTO(article);
    }

    @Transactional
    public ArticleDto update(Long id, ArticleUpdateRequest req, String username) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Статья не найдена"));

        User current = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        validateEditRights(article, current);

        // --- Обновляем запись ---
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            article.setCategory(category);
        }

        if (req.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(req.getTagIds()));
            article.setTags(tags);
        }

        article.setUpdatedAt(LocalDateTime.now());

        return mapper.toDTO(articleRepository.save(article));
    }

    public void delete(Long id, String username) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Статья не найдена"));

        User current = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        validateEditRights(article, current);

        articleRepository.delete(article);
    }

    public List<ArticleDto> search(String query) {
        return mapper.toDTO(articleRepository.findByTitleContainingIgnoreCase(query));
    }

    public List<ArticleDto> findByTag(String tagName) {
        return mapper.toDTO(articleRepository.findByTagsName(tagName));
    }

    public List<ArticleDto> findByCategory(Long categoryId) {
        return mapper.toDTO(articleRepository.findByCategoryId(categoryId));
    }

    private void validateEditRights(Article article, User current) {


        if (current.getRole() == Role.ADMIN) return;

        if (article.getAuthor() == null || article.getAuthor().getUser() == null) {
            throw new RuntimeException("Статья создана некорректно — у неё нет автора");
        }

        Long ownerId = article.getAuthor().getUser().getId();
        if (!Objects.equals(ownerId, current.getId())) {
            throw new RuntimeException("Нет прав редактировать эту статью");
        }
    }


}