
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateMovieUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteMovieUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllMoviesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetMovieByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateMovieUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class MovieController {

  private final CreateMovieUseCase createMovieUseCase;
  private final UpdateMovieUseCase updateMovieUseCase;
  private final DeleteMovieUseCase deleteMovieUseCase;
  private final GetMovieByIdUseCase getMovieByIdUseCase;
  private final GetAllMoviesUseCase getAllMoviesUseCase;

  public MovieModel createMovie(final CreateMovieCommand command) {
    return createMovieUseCase.execute(command);
  }

  public MovieModel updateMovie(final UpdateMovieCommand command) {
    return updateMovieUseCase.execute(command);
  }

  public void deleteMovie(final DeleteMovieCommand command) {
    deleteMovieUseCase.execute(command);
  }

  public MovieModel findMovieById(final GetMovieByIdQuery query) {
    return getMovieByIdUseCase.execute(query);
  }

  public List<MovieModel> listAllMovies() {
    return getAllMoviesUseCase.execute();
  }
}