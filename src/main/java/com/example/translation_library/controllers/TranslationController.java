package com.example.translation_library.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.translation_library.domain.dto.TranslationDto;
import com.example.translation_library.domain.entities.TranslationEntity;
import com.example.translation_library.services.TranslationService;
import com.example.translation_library.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TranslationController {
    private TranslationService translationService;

    private Mapper<TranslationEntity, TranslationDto> translationMapper;

    public TranslationController(TranslationService translationService, Mapper<TranslationEntity, TranslationDto> translationMapper) {
        this.translationService = translationService;
        this.translationMapper = translationMapper;
    }
    
    @PostMapping("/")
    public ResponseEntity<TranslationDto> addTranslation(@RequestBody TranslationDto translationDto) {
        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);
        translationEntity = translationService.addTranslation(translationEntity);
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.CREATED);
    }
    

    @GetMapping("/{languageCode}/{word}")
    public ResponseEntity<TranslationDto> getTranslation(@PathVariable String languageCode, @PathVariable String word) {
        TranslationEntity translationEntity = translationService.getTranslation(languageCode, word).orElse(null);
        if (translationEntity != null) {
            return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
