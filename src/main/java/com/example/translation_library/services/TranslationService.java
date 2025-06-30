package com.example.translation_library.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.translation_library.domain.entities.TranslationEntity;

@Service
public interface TranslationService {
    TranslationEntity save(TranslationEntity translation);

    Optional<TranslationEntity> getTranslation(String languageCode, String word);

    Boolean exists(String languageCode, String word);
}
