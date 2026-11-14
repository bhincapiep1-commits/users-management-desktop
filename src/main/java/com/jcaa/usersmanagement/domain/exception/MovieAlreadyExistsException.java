package com.jcaa.usersmanagement.domain.exception;

public final class MovieAlreadyExistsException extends DomainException {

  private static final String MESSAGE_ID_EXISTS = "A movie with id '%s' already exists.";

  private MovieAlreadyExistsException(final String message) {
    super(message);
  }

  public static MovieAlreadyExistsException becauseIdAlreadyExists(final String movieId) {
    return new MovieAlreadyExistsException(String.format(MESSAGE_ID_EXISTS, movieId));
  }
}