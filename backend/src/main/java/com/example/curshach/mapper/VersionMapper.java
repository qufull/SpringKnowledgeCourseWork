package com.example.curshach.mapper;

import com.example.curshach.dto.VersionDto;
import com.example.curshach.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VersionMapper {
    VersionDto toDTO(Version version);
}
