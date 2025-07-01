package com.example.translation_library.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.translation_library.domain.dto.UserDto;
import com.example.translation_library.domain.entities.UserEntity;
import com.example.translation_library.mappers.Mapper;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapTo(UserEntity entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto dto) {
        return modelMapper.map(dto, UserEntity.class);
    }
}
