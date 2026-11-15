package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record MovieEntity(
    String id,
    String distributionTitle,
    String originalTitle,
    String genre,
    String originalLanguage,
    boolean spanishSubtitles,
    int productionYear,
    String officialWebsite,
    int durationMinutes,
    String ageRating,
    String releaseDateSantiago,
    String summary) {}
