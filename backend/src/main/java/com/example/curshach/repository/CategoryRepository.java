package com.example.curshach.repository;

import com.example.curshach.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByNameIgnoreCase(String name);
}
