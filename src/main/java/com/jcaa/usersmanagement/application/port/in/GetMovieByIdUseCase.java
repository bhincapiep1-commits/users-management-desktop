package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetMovieByIdUseCase {
  MovieModel execute(@NotNull @Valid GetMovieByIdQuery query);
}