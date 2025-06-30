package com.example.translation_library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.translation_library.domain.TranslationId;
import com.example.translation_library.domain.entities.TranslationEntity;

@Repository
public interface TranslationRepository extends CrudRepository<TranslationEntity, TranslationId> {
    
}
