package com.example.translation_library.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.translation_library.domain.dto.TranslationDto;
import com.example.translation_library.domain.entities.TranslationEntity;
import com.example.translation_library.mappers.Mapper;

@Component
public class TranslationMapperImpl implements Mapper<TranslationEntity, TranslationDto> {
    
    private ModelMapper modelMapper;

    public TranslationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TranslationDto mapTo(TranslationEntity entity) {
        return modelMapper.map(entity, TranslationDto.class);
    }

    @Override
    public TranslationEntity mapFrom(TranslationDto dto) {
        return modelMapper.map(dto, TranslationEntity.class);
    }
    
}
