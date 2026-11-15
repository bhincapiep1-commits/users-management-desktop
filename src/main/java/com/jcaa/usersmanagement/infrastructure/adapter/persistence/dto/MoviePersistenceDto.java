package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

import java.util.List;

public record MoviePersistenceDto(
    String id,
    String distributionTitle,
    String originalTitle,
    String genre,
    String originalLanguage,
    boolean spanishSubtitles,
    List<String> countriesOfOrigin,
    int productionYear,
    String officialWebsite,
    int durationMinutes,
    String ageRating,
    String releaseDateSantiago,
    String summary) {}
