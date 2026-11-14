package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.util.Optional;

public interface GetMovieByIdPort {
  Optional<MovieModel> getById(MovieId movieId);
}
