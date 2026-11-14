package com.jcaa.usersmanagement.domain.exception;

public final class MovieNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The movie with id '%s' was not found.";

  private MovieNotFoundException(final String message) {
    super(message);
  }

  public static MovieNotFoundException becauseIdWasNotFound(final String movieId) {
    return new MovieNotFoundException(String.format(MESSAGE_BY_ID, movieId));
  }
}