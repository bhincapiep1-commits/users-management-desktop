package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidMovieIdException;
import java.util.Objects;

public record MovieId(String value) {

  public MovieId {
    final String normalizedValue = Objects.requireNonNull(value, "MovieId cannot be null").trim();
    validateNotEmpty(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidMovieIdException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}