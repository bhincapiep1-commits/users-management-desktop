package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.Value;

@Value
public class MovieModel {

  MovieId id;
  String distributionTitle;
  String originalTitle;
  String genre;
  String originalLanguage;
  boolean spanishSubtitles;
  List<String> countriesOfOrigin;
  int productionYear;
  String officialWebsite;
  int durationMinutes;
  AgeRating ageRating;
  LocalDate releaseDateSantiago;
  String summary;

  public static MovieModel create(
      final MovieId id,
      final String distributionTitle,
      final String originalTitle,
      final String genre,
      final String originalLanguage,
      final boolean spanishSubtitles,
      final List<String> countriesOfOrigin,
      final int productionYear,
      final String officialWebsite,
      final int durationMinutes,
      final AgeRating ageRating,
      final LocalDate releaseDateSantiago,
      final String summary) {

    Objects.requireNonNull(id, "Movie ID cannot be null");
    Objects.requireNonNull(ageRating, "Age rating cannot be null");
    Objects.requireNonNull(releaseDateSantiago, "Release date cannot be null");

    validateText(distributionTitle, "Distribution title");
    validateText(originalTitle, "Original title");
    validateText(genre, "Genre");
    validateText(originalLanguage, "Original language");
    validateText(officialWebsite, "Official website");
    validateText(summary, "Summary");

    if (countriesOfOrigin == null || countriesOfOrigin.isEmpty()) {
      throw new IllegalArgumentException("Countries of origin cannot be empty");
    }

    List<String> normalizedCountries = countriesOfOrigin.stream()
        .map(country -> validateText(country, "Country of origin"))
        .toList();

    if (productionYear <= 0) {
      throw new IllegalArgumentException("Production year must be greater than zero");
    }

    if (durationMinutes <= 0) {
      throw new IllegalArgumentException("Duration must be greater than zero");
    }

    return new MovieModel(
        id,
        distributionTitle.trim(),
        originalTitle.trim(),
        genre.trim(),
        originalLanguage.trim(),
        spanishSubtitles,
        List.copyOf(normalizedCountries),
        productionYear,
        officialWebsite.trim(),
        durationMinutes,
        ageRating,
        releaseDateSantiago,
        summary.trim());
  }

  private static String validateText(final String value, final String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be empty");
    }
    return value.trim();
  }
}