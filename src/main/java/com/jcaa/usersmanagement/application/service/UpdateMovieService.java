
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateMovieUseCase;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateMoviePort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
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
public final class UpdateMovieService implements UpdateMovieUseCase {

  private final GetMovieByIdPort getMovieByIdPort;
  private final UpdateMoviePort updateMoviePort;
  private final Validator validator;

  @Override
  public MovieModel execute(final UpdateMovieCommand command) {
    validateCommand(command);

    final MovieId movieId = new MovieId(command.id());

    getMovieByIdPort
        .getById(movieId)
        .orElseThrow(
            () -> MovieNotFoundException.becauseIdWasNotFound(movieId.value()));

    final MovieModel movieToUpdate =
        MovieApplicationMapper.fromUpdateCommandToModel(command);

    return updateMoviePort.update(movieToUpdate);
  }

  private void validateCommand(final UpdateMovieCommand command) {
    final Set<ConstraintViolation<UpdateMovieCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}