package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovieModelTest {

  private MovieModel createValidMovie() {
    return MovieModel.create(
        new MovieId("MOV-001"),
        "Interestelar",
        "Interstellar",
        "Ciencia ficción",
        "Inglés",
        true,
        List.of("Estados Unidos", "Reino Unido"),
        2014,
        "https://www.interstellarmovie.net",
        169,
        AgeRating.PLUS_15,
        LocalDate.of(2014, 11, 6),
        "Un grupo de exploradores viaja por el espacio.");
  }

  @Test
  @DisplayName("Should create a movie with valid information")
  void shouldCreateMovieWithValidInformation() {
    MovieModel movie = createValidMovie();

    assertEquals("MOV-001", movie.getId().value());
    assertEquals("Interestelar", movie.getDistributionTitle());
    assertEquals("Interstellar", movie.getOriginalTitle());
    assertEquals(AgeRating.PLUS_15, movie.getAgeRating());
    assertEquals(169, movie.getDurationMinutes());
  }

  @Test
  @DisplayName("Should reject empty distribution title")
  void shouldRejectEmptyDistributionTitle() {
    assertThrows(IllegalArgumentException.class, () ->
        MovieModel.create(
            new MovieId("MOV-002"), " ", "Original", "Drama", "Inglés",
            true, List.of("Colombia"), 2020, "https://example.com",
            100, AgeRating.ALL, LocalDate.now(), "Resumen"));
  }

  @Test
  @DisplayName("Should reject empty countries of origin")
  void shouldRejectEmptyCountriesOfOrigin() {
    assertThrows(IllegalArgumentException.class, () ->
        MovieModel.create(
            new MovieId("MOV-003"), "Título", "Original", "Drama", "Español",
            false, List.of(), 2020, "https://example.com",
            100, AgeRating.ALL, LocalDate.now(), "Resumen"));
  }

  @Test
  @DisplayName("Should reject invalid production year")
  void shouldRejectInvalidProductionYear() {
    assertThrows(IllegalArgumentException.class, () ->
        MovieModel.create(
            new MovieId("MOV-004"), "Título", "Original", "Drama", "Español",
            false, List.of("Colombia"), 0, "https://example.com",
            100, AgeRating.ALL, LocalDate.now(), "Resumen"));
  }

  @Test
  @DisplayName("Should reject invalid duration")
  void shouldRejectInvalidDuration() {
    assertThrows(IllegalArgumentException.class, () ->
        MovieModel.create(
            new MovieId("MOV-005"), "Título", "Original", "Drama", "Español",
            false, List.of("Colombia"), 2020, "https://example.com",
            0, AgeRating.ALL, LocalDate.now(), "Resumen"));
  }

  @Test
  @DisplayName("Should not allow external modification of countries")
  void shouldNotAllowExternalModificationOfCountries() {
    List<String> countries = new ArrayList<>();
    countries.add("Colombia");

    MovieModel movie = MovieModel.create(
        new MovieId("MOV-006"),
        "Título",
        "Original",
        "Drama",
        "Español",
        false,
        countries,
        2020,
        "https://example.com",
        100,
        AgeRating.ALL,
        LocalDate.now(),
        "Resumen");

    countries.add("Argentina");

    assertEquals(1, movie.getCountriesOfOrigin().size());
  }
}