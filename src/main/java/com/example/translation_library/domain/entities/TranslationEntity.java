package com.example.translation_library.domain.entities;

import com.example.translation_library.domain.TranslationId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "translations")
@IdClass(TranslationId.class)
public class TranslationEntity {

    @Id
    @Column(length = 2, nullable = false)
    private String languageCode;

    @Id
    @Column(length = 255, nullable = false)
    private String word;

    @Column(length = 255, nullable = false)
    private String translation;
}
