package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MovieRepositoryMemory")
class MovieRepositoryMemoryTest {

  private MovieRepositoryMemory repository;
  private MovieModel movie;
  private MovieId movieId;

  @BeforeEach
  void setUp() {
    repository = new MovieRepositoryMemory();
    movieId = new MovieId("M-001");
    movie = createMovie("M-001", "Interestelar");
  }

  private MovieModel createMovie(final String id, final String title) {
    return MovieModel.create(
        new MovieId(id),
        title,
        "Interstellar",
        "Ciencia ficcion",
        "Ingles",
        true,
        List.of("Estados Unidos"),
        2014,
        "https://www.example.com",
        169,
        AgeRating.PLUS_9,
        LocalDate.of(2014, 11, 6),
        "Una aventura espacial.");
  }

  @Test
  @DisplayName("save() stores and returns the movie")
  void shouldSaveMovie() {
    final MovieModel result = repository.save(movie);

    assertAll(
        () -> assertEquals(movie, result),
        () -> assertEquals(Optional.of(movie), repository.getById(movieId)));
  }

  @Test
  @DisplayName("getById() returns the movie when it exists")
  void shouldFindMovieById() {
    repository.save(movie);

    final Optional<MovieModel> result = repository.getById(movieId);

    assertTrue(result.isPresent());
    assertEquals(movie, result.get());
  }

  @Test
  @DisplayName("getById() returns empty when the movie does not exist")
  void shouldReturnEmptyWhenMovieDoesNotExist() {
    assertTrue(repository.getById(new MovieId("M-999")).isEmpty());
  }

  @Test
  @DisplayName("getAll() returns all stored movies")
  void shouldReturnAllMovies() {
    final MovieModel secondMovie = createMovie("M-002", "Avatar");
    repository.save(movie);
    repository.save(secondMovie);

    final List<MovieModel> result = repository.getAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(movie));
    assertTrue(result.contains(secondMovie));
  }

  @Test
  @DisplayName("getAll() returns an empty list when there are no movies")
  void shouldReturnEmptyListWhenThereAreNoMovies() {
    assertTrue(repository.getAll().isEmpty());
  }

  @Test
  @DisplayName("update() replaces the stored movie")
  void shouldUpdateMovie() {
    repository.save(movie);
    final MovieModel updatedMovie = createMovie("M-001", "Interestelar actualizado");

    final MovieModel result = repository.update(updatedMovie);

    assertAll(
        () -> assertEquals(updatedMovie, result),
        () -> assertEquals(Optional.of(updatedMovie), repository.getById(movieId)));
  }

  @Test
  @DisplayName("delete() removes the movie")
  void shouldDeleteMovie() {
    repository.save(movie);

    repository.delete(movieId);

    assertTrue(repository.getById(movieId).isEmpty());
    assertTrue(repository.getAll().isEmpty());
  }

  @Test
  @DisplayName("delete() does nothing when the movie does not exist")
  void shouldDoNothingWhenDeletingNonExistingMovie() {
    assertDoesNotThrow(() -> repository.delete(new MovieId("M-999")));
    assertTrue(repository.getAll().isEmpty());
  }
}
