package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.MovieModel;

public interface UpdateMoviePort {
  MovieModel update(MovieModel movie);
}