package com.example.curshach.repository;

import com.example.curshach.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

    @EntityGraph(attributePaths = {"author"})
    List<Article> findAll();

    @EntityGraph(attributePaths = {
            "author",
            "comments",
            "comments.user"
    })
    @Query("SELECT a FROM Article a WHERE a.id = :id")
   Optional<Article> findFullById(Long id);

    @EntityGraph(attributePaths = {"tags", "category"})
    Optional<Article> findWithTagsAndCategoryById(Long id);

    @EntityGraph(attributePaths = {"author","author.user"})
    Optional<Article> findById(Long id);

    List<Article> findByAuthorUserUsername(String username);

    @EntityGraph(attributePaths = {"author"})
    List<Article> findByCategoryId(Long categoryId);
    @EntityGraph(attributePaths = {"author"})
    List<Article> findByTagsName(String tagName);
    @EntityGraph(attributePaths = {"author"})
    List<Article> findByTitleContainingIgnoreCase(String query);
}
