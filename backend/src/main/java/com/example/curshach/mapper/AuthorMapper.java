package com.example.curshach.mapper;

import com.example.curshach.dto.AuthorDto;
import com.example.curshach.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    @Mapping(source = "user.username", target = "username")
    AuthorDto toDto(Author author);
}
