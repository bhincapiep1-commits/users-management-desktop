package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteMoviePort;
import com.jcaa.usersmanagement.application.port.out.GetAllMoviesPort;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveMoviePort;
import com.jcaa.usersmanagement.application.port.out.UpdateMoviePort;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MovieRepositoryMemory implements
    SaveMoviePort,
    GetMovieByIdPort,
    GetAllMoviesPort,
    UpdateMoviePort,
    DeleteMoviePort {

  private final ConcurrentMap<MovieId, MovieModel> movies = new ConcurrentHashMap<>();

  @Override
  public MovieModel save(final MovieModel movie) {
    movies.put(movie.getId(), movie);
    return movie;
  }

  @Override
  public Optional<MovieModel> getById(final MovieId movieId) {
    return Optional.ofNullable(movies.get(movieId));
  }

  @Override
  public List<MovieModel> getAll() {
    return List.copyOf(movies.values());
  }

  @Override
  public MovieModel update(final MovieModel movie) {
    movies.put(movie.getId(), movie);
    return movie;
  }

  @Override
  public void delete(final MovieId movieId) {
    movies.remove(movieId);
  }
}
