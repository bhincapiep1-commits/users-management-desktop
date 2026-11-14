
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetMovieByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.MovieApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.MovieNotFoundException;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetMovieByIdService implements GetMovieByIdUseCase {

  private final GetMovieByIdPort getMovieByIdPort;
  private final Validator validator;

  @Override
  public MovieModel execute(final GetMovieByIdQuery query) {
    validateQuery(query);

    final MovieId movieId =
        MovieApplicationMapper.fromGetMovieByIdQueryToMovieId(query);

    return getMovieByIdPort
        .getById(movieId)
        .orElseThrow(
            () -> MovieNotFoundException.becauseIdWasNotFound(movieId.value()));
  }

  private void validateQuery(final GetMovieByIdQuery query) {
    final Set<ConstraintViolation<GetMovieByIdQuery>> violations =
        validator.validate(query);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}