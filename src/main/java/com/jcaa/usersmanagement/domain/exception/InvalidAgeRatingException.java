package com.jcaa.usersmanagement.domain.exception;

public final class InvalidAgeRatingException extends DomainException {

  private static final String MESSAGE_INVALID = "The age rating '%s' is not valid.";

  private InvalidAgeRatingException(final String message) {
    super(message);
  }

  public static InvalidAgeRatingException becauseValueIsInvalid(final String rating) {
    return new InvalidAgeRatingException(String.format(MESSAGE_INVALID, rating));
  }
}