package com.example.curshach.mapper;

import com.example.curshach.dto.UserDto;
import com.example.curshach.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);
}
