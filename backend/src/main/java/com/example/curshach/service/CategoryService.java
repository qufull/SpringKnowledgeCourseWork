package com.example.curshach.service;

import com.example.curshach.dto.CategoryDto;
import com.example.curshach.mapper.CategoryMapper;
import com.example.curshach.model.Category;
import com.example.curshach.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDto create(CategoryDto dto) {
        Category c = new Category();
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        return categoryMapper.toDto(categoryRepository.save(c));
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        c.setName(dto.getName());
        c.setDescription(dto.getDescription());

        return categoryMapper.toDto(categoryRepository.save(c));
    }


    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
