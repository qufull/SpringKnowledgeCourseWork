package com.example.curshach.mapper;

import com.example.curshach.dto.TagDto;
import com.example.curshach.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    TagDto toDTO(Tag tag);

    List<TagDto> toDTO(List<Tag> tag);
}
