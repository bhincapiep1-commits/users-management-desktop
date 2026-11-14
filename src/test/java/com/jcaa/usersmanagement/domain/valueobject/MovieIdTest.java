package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidMovieIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovieIdTest {

  @Test
  @DisplayName("Should create MovieId with valid value")
  void shouldCreateMovieIdWithValidValue() {
    MovieId movieId = new MovieId("MOV-001");

    assertEquals("MOV-001", movieId.value());
  }

  @Test
  @DisplayName("Should trim MovieId value")
  void shouldTrimMovieIdValue() {
    MovieId movieId = new MovieId("  MOV-001  ");

    assertEquals("MOV-001", movieId.value());
  }

  @Test
  @DisplayName("Should throw exception when MovieId is empty")
  void shouldThrowExceptionWhenMovieIdIsEmpty() {
    assertThrows(InvalidMovieIdException.class, () -> new MovieId(""));
  }

  @Test
  @DisplayName("Should throw exception when MovieId contains only spaces")
  void shouldThrowExceptionWhenMovieIdContainsOnlySpaces() {
    assertThrows(InvalidMovieIdException.class, () -> new MovieId("   "));
  }

  @Test
  @DisplayName("Should throw exception when MovieId is null")
  void shouldThrowExceptionWhenMovieIdIsNull() {
    assertThrows(NullPointerException.class, () -> new MovieId(null));
  }
}