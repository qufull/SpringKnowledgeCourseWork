package com.example.curshach.mapper;

import com.example.curshach.dto.CategoryDto;
import com.example.curshach.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    List<CategoryDto> toDto(List<Category> category);
}
