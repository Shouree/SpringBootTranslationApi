package com.example.translation_library.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.translation_library.domain.dto.TranslationDto;
import com.example.translation_library.domain.dto.UserDto;
import com.example.translation_library.domain.entities.TranslationEntity;
import com.example.translation_library.domain.entities.UserEntity;
import com.example.translation_library.services.CustomUserDetailsService;
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
    private CustomUserDetailsService userDetailsService;

    private Mapper<TranslationEntity, TranslationDto> translationMapper;
    private Mapper<UserEntity, UserDto> userMapper;

    public TranslationController(TranslationService translationService,
                                 CustomUserDetailsService userDetailsService, 
                                 Mapper<TranslationEntity, TranslationDto> translationMapper, 
                                 Mapper<UserEntity, UserDto> userMapper) {
        this.translationService = translationService;
        this.userDetailsService = userDetailsService;
        this.translationMapper = translationMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserDto userDto) {
        if(userDetailsService.exists(userDto.getUsername())) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }
        
        UserEntity userEntity = userMapper.mapFrom(userDto);
        userEntity = userDetailsService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.CREATED);
    }
    
    
    @GetMapping("/")
    public ResponseEntity<?> viewIndexPage() {
        return new ResponseEntity<>("Welcome to the Translation Library API!", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addTranslation(@RequestBody TranslationDto translationDto) {
        if(translationService.exists(translationDto.getLanguageCode(), translationDto.getWord()))
            return new ResponseEntity<>("Translation already exists.", HttpStatus.BAD_REQUEST);

        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);
        translationEntity = translationService.save(translationEntity);
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.CREATED);
    }

    @GetMapping("/{languageCode}/{word}")
    public ResponseEntity<?> getTranslation(@PathVariable String languageCode, @PathVariable String word) {
        TranslationEntity translationEntity = translationService.get(languageCode, word).orElse(null);

        if (translationEntity == null)
            return new ResponseEntity<>("No translation for " + word + " in " + languageCode + " exists.", HttpStatus.NOT_FOUND);
            
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
    }

    /*@PutMapping("/{languageCode}/{word}")
    public ResponseEntity<?> updateTranslation(@PathVariable String languageCode, @PathVariable String word, @RequestBody TranslationDto translationDto) {
        if(!translationService.exists(languageCode, word))
            return new ResponseEntity<>("Translation for " + word + " in " + languageCode + " does not exist.", HttpStatus.NOT_FOUND);
        
        if(!languageCode.equals(translationDto.getLanguageCode()) || !word.equals(translationDto.getWord()))
            return new ResponseEntity<>("Language code or word does not correspond to URL.", HttpStatus.BAD_REQUEST);
        
        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);    
        translationService.save(translationEntity);
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
    }*/

    @PutMapping("/{languageCode}/{word}")
    public ResponseEntity<?> updateTranslation(@PathVariable String languageCode, @PathVariable String word, @RequestBody TranslationDto translationDto) {
        if(!translationService.exists(languageCode, word))
            return new ResponseEntity<>("Translation for " + word + " in " + languageCode + " does not exist.", HttpStatus.NOT_FOUND);
        
        translationDto.setLanguageCode(languageCode);
        translationDto.setWord(word);
        TranslationEntity translationEntity = translationMapper.mapFrom(translationDto);    
        translationService.save(translationEntity);
        return new ResponseEntity<>(translationMapper.mapTo(translationEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{languageCode}/{word}")
    public ResponseEntity<?> deleteTranslation(@PathVariable String languageCode, @PathVariable String word) {
        if(!translationService.exists(languageCode, word))
            return new ResponseEntity<>("Translation for " + word + " in " + languageCode + " does not exist.", HttpStatus.NOT_FOUND);
        
        translationService.delete(languageCode, word);
        return new ResponseEntity<>("Translation for " + word + " in " + languageCode + " deleted.", HttpStatus.OK);
    }
}
