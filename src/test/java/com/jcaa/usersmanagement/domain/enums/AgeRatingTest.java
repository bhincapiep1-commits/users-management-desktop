package com.jcaa.usersmanagement.domain.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidAgeRatingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AgeRatingTest {

  @Test
  @DisplayName("Should return correct AgeRating for valid input")
  void shouldReturnCorrectAgeRatingForValidInput() {
    assertEquals(AgeRating.ALL, AgeRating.fromString("ALL"));
    assertEquals(AgeRating.PLUS_9, AgeRating.fromString("PLUS_9"));
    assertEquals(AgeRating.PLUS_15, AgeRating.fromString("PLUS_15"));
    assertEquals(AgeRating.PLUS_18, AgeRating.fromString("PLUS_18"));
  }

  @Test
  @DisplayName("Should accept lowercase input")
  void shouldAcceptLowercaseInput() {
    assertEquals(AgeRating.PLUS_15, AgeRating.fromString("plus_15"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "INVALID_RATING"})
  @DisplayName("Should throw InvalidAgeRatingException when input is invalid")
  void shouldThrowExceptionWhenInputIsInvalid(String input) {
    assertThrows(InvalidAgeRatingException.class, () -> AgeRating.fromString(input));
  }
}