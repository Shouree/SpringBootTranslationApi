package com.example.translation_library.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.translation_library.domain.TranslationId;
import com.example.translation_library.domain.entities.TranslationEntity;
import com.example.translation_library.repositories.TranslationRepository;
import com.example.translation_library.services.TranslationService;

@Service
public class TranslationServiceImpl implements TranslationService {
    
    private TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public TranslationEntity addTranslation(TranslationEntity translationEntity) {
        return translationRepository.save(translationEntity);
    }

    @Override
    public Optional<TranslationEntity> getTranslation(String languageCode, String word) {
        return translationRepository.findById(new TranslationId(languageCode, word));
    }
}
