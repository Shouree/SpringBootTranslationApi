package com.example.translation_library.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.translation_library.domain.dto.TranslationDto;
import com.example.translation_library.domain.entities.TranslationEntity;
import com.example.translation_library.services.TranslationService;
import com.example.translation_library.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class TranslationController {
    private TranslationService translationService;

    private Mapper<TranslationEntity, TranslationDto> translationMapper;

    public TranslationController(TranslationService translationService, Mapper<TranslationEntity, TranslationDto> translationMapper) {
        this.translationService = translationService;
        this.translationMapper = translationMapper;
    }
    
    @PostMapping("/")
    public ResponseEntity<?> addTranslation(@RequestBody TranslationDto translationDto) {
        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);
        translationEntity = translationService.save(translationEntity);

        if(translationEntity != null)
            return new ResponseEntity<>("Translation already exists.", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.CREATED);
    }
    

    @GetMapping("/{languageCode}/{word}")
    public ResponseEntity<?> getTranslation(@PathVariable String languageCode, @PathVariable String word) {
        TranslationEntity translationEntity = translationService.getTranslation(languageCode, word).orElse(null);

        if (translationEntity == null)
            return new ResponseEntity<>("No translation for " + word + " in " + languageCode + " exists.", HttpStatus.NOT_FOUND);
            
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
    }

    @PutMapping("/{languageCode}/{word}")
    public ResponseEntity<?> putMethodName(@PathVariable String languageCode, @PathVariable String word, @RequestBody TranslationDto translationDto) {
        if(!translationService.exists(languageCode, word))
            return new ResponseEntity<>("Translation for " + word + " in " + languageCode + " does not exist.", HttpStatus.NOT_FOUND);
        
        if(!languageCode.equals(translationDto.getLanguageCode()) || !word.equals(translationDto.getWord()))
            return new ResponseEntity<>("Language code or word does not correspond to URL.", HttpStatus.BAD_REQUEST);
        
        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);    
        translationService.save(translationEntity);
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
    }
}
