package com.example.curshach.mapper;

import com.example.curshach.dto.CommentDto;
import com.example.curshach.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mapping(source = "user.username", target = "userName")
    CommentDto toDTO(Comment comment);

    List<CommentDto> toDTO(List<Comment> comment);
}
