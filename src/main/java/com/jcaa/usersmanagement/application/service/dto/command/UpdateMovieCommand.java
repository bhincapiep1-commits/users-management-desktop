package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record UpdateMovieCommand(
    @NotBlank String id,
    @NotBlank @Size(max = 150) String distributionTitle,
    @NotBlank @Size(max = 150) String originalTitle,
    @NotBlank String genre,
    @NotBlank String originalLanguage,
    boolean spanishSubtitles,
    @NotEmpty List<String> countriesOfOrigin,
    @Positive int productionYear,
    @NotBlank String officialWebsite,
    @Positive int durationMinutes,
    @NotBlank String ageRating,
    @NotNull LocalDate releaseDateSantiago,
    @NotBlank String summary) {}