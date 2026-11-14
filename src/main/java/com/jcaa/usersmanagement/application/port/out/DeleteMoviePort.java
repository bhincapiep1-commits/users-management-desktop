package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.MovieId;

public interface DeleteMoviePort {
  void delete(MovieId movieId);
}
