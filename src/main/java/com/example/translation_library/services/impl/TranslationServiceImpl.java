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
    public TranslationEntity save(TranslationEntity translationEntity) {
        return translationRepository.save(translationEntity);
    }

    @Override
    public Optional<TranslationEntity> get(String languageCode, String word) {
        return translationRepository.findById(new TranslationId(languageCode, word));
    }

    @Override
    public void delete(String languageCode, String word) {
        translationRepository.deleteById(new TranslationId(languageCode, word));
    }

    @Override
    public Boolean exists(String languageCode, String word) {
        return translationRepository.existsById(new TranslationId(languageCode, word));
    }
}
