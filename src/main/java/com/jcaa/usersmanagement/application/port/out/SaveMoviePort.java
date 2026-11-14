package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.MovieModel;

public interface SaveMoviePort {
  MovieModel save(MovieModel movie);
}
