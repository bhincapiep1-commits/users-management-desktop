package com.jcaa.usersmanagement.domain.exception;

public final class InvalidMovieIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The movie ID cannot be empty.";

  private InvalidMovieIdException(final String message) {
    super(message);
  }

  public static InvalidMovieIdException becauseValueIsEmpty() {
    return new InvalidMovieIdException(MESSAGE_EMPTY);
  }
}