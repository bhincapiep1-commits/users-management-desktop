package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidAgeRatingException;

public enum AgeRating {
  ALL,
  PLUS_9,
  PLUS_15,
  PLUS_18;

  public static AgeRating fromString(final String value) {
    for (final AgeRating rating : values()) {
      if (rating.name().equalsIgnoreCase(value)) {
        return rating;
      }
    }
    throw InvalidAgeRatingException.becauseValueIsInvalid(value);
  }
}