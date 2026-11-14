
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteMovieUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteMoviePort;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.application.service.mapper.MovieApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.MovieNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteMovieService implements DeleteMovieUseCase {

  private final GetMovieByIdPort getMovieByIdPort;
  private final DeleteMoviePort deleteMoviePort;
  private final Validator validator;

  @Override
  public void execute(final DeleteMovieCommand command) {
    validateCommand(command);

    final MovieId movieId =
        MovieApplicationMapper.fromDeleteCommandToMovieId(command);

    getMovieByIdPort
        .getById(movieId)
        .orElseThrow(
            () -> MovieNotFoundException.becauseIdWasNotFound(movieId.value()));

    deleteMoviePort.delete(movieId);
  }

  private void validateCommand(final DeleteMovieCommand command) {
    final Set<ConstraintViolation<DeleteMovieCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}