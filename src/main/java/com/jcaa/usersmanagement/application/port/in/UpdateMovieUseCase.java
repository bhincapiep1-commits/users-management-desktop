package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateMovieUseCase {
  MovieModel execute(@NotNull @Valid UpdateMovieCommand command);
}