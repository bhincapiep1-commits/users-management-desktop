
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateMovieUseCase;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveMoviePort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.application.service.mapper.MovieApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.MovieAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateMovieService implements CreateMovieUseCase {

  private final SaveMoviePort saveMoviePort;
  private final GetMovieByIdPort getMovieByIdPort;
  private final Validator validator;

  @Override
  public MovieModel execute(final CreateMovieCommand command) {
    validateCommand(command);

    final MovieId movieId = new MovieId(command.id());

    getMovieByIdPort
        .getById(movieId)
        .ifPresent(
            ignored -> {
              throw MovieAlreadyExistsException.becauseIdAlreadyExists(movieId.value());
            });

    final MovieModel movieToSave =
        MovieApplicationMapper.fromCreateCommandToModel(command);

    return saveMoviePort.save(movieToSave);
  }

  private void validateCommand(final CreateMovieCommand command) {
    final Set<ConstraintViolation<CreateMovieCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}