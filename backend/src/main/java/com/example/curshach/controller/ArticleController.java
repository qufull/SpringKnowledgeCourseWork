package com.example.curshach.controller;

import com.example.curshach.dto.articles.ArticleCardGetRequest;
import com.example.curshach.dto.articles.ArticleCreateRequest;
import com.example.curshach.dto.articles.ArticleDto;
import com.example.curshach.dto.articles.ArticleUpdateGetResponse;
import com.example.curshach.dto.articles.ArticleUpdateRequest;
import com.example.curshach.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDto> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public ArticleCardGetRequest getById(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @GetMapping("/update/{id}")
    public ArticleUpdateGetResponse getByIdWithTagsAndCategory(@PathVariable Long id){ return articleService.getByIdWithTagsAndCategory(id);}

    @GetMapping("/search")
    public List<ArticleDto> search(@RequestParam("q") String query) {
        return articleService.search(query);
    }

    @GetMapping("/by-tag/{tag}")
    public List<ArticleDto> byTag(@PathVariable String tag) {
        return articleService.findByTag(tag);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ArticleDto> byCategory(@PathVariable Long categoryId) {
        return articleService.findByCategory(categoryId);
    }

    @PostMapping
    public ArticleDto create(@RequestBody ArticleCreateRequest req,
                             Principal principal) {
        return articleService.create(req,principal.getName());
    }

    @PutMapping("/{id}")
    public ArticleDto update(@PathVariable Long id, @RequestBody ArticleUpdateRequest article, Principal principal) {
        return articleService.update(id, article, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        articleService.delete(id, principal.getName());
    }

    @GetMapping("/my")
    public List<ArticleDto> getMyArticles(Principal principal) {
        System.out.println(principal.getName());
        return articleService.getMyArticles(principal.getName());
    }

}
