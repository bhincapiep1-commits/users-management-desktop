package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteMoviePort;
import com.jcaa.usersmanagement.application.port.out.GetAllMoviesPort;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveMoviePort;
import com.jcaa.usersmanagement.application.port.out.UpdateMoviePort;
import com.jcaa.usersmanagement.domain.exception.MovieNotFoundException;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.MoviePersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.MoviePersistenceMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public final class MovieRepositoryMySQL
    implements SaveMoviePort,
        UpdateMoviePort,
        GetMovieByIdPort,
        GetAllMoviesPort,
        DeleteMoviePort {

  private static final String SQL_INSERT =
      "INSERT INTO movies (id, distribution_title, original_title, genre, "
          + "original_language, spanish_subtitles, production_year, official_website, "
          + "duration_minutes, age_rating, release_date_santiago, summary) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE movies SET distribution_title = ?, original_title = ?, genre = ?, "
          + "original_language = ?, spanish_subtitles = ?, production_year = ?, "
          + "official_website = ?, duration_minutes = ?, age_rating = ?, "
          + "release_date_santiago = ?, summary = ? WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT * FROM movies WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT * FROM movies ORDER BY distribution_title ASC";

  private static final String SQL_SELECT_COUNTRIES =
      "SELECT movie_id, country FROM movie_countries ORDER BY movie_id, country";

  private static final String SQL_SELECT_COUNTRIES_BY_ID =
      "SELECT country FROM movie_countries WHERE movie_id = ? ORDER BY country";

  private static final String SQL_INSERT_COUNTRY =
      "INSERT INTO movie_countries (movie_id, country) VALUES (?, ?)";

  private static final String SQL_DELETE_COUNTRIES =
      "DELETE FROM movie_countries WHERE movie_id = ?";

  private static final String SQL_EXISTS =
      "SELECT 1 FROM movies WHERE id = ? LIMIT 1";

  private static final String SQL_DELETE =
      "DELETE FROM movies WHERE id = ?";

  private final Connection connection;

  @Override
  public MovieModel save(final MovieModel movie) {
    final MoviePersistenceDto dto = MoviePersistenceMapper.fromModelToDto(movie);

    try {
      executeTransaction(() -> {
        insertMovie(dto);
        insertCountries(dto);
      });
      return findByIdOrFail(movie.getId());
    } catch (final SQLException exception) {
      throw PersistenceException.becauseMovieSaveFailed(dto.id(), exception);
    }
  }

  @Override
  public MovieModel update(final MovieModel movie) {
    final MoviePersistenceDto dto = MoviePersistenceMapper.fromModelToDto(movie);

    try {
      executeTransaction(() -> {
        if (!exists(dto.id())) {
          throw MovieNotFoundException.becauseIdWasNotFound(dto.id());
        }
        updateMovie(dto);
        deleteCountries(dto.id());
        insertCountries(dto);
      });
      return findByIdOrFail(movie.getId());
    } catch (final SQLException exception) {
      throw PersistenceException.becauseMovieUpdateFailed(dto.id(), exception);
    }
  }

  @Override
  public Optional<MovieModel> getById(final MovieId movieId) {
    try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, movieId.value());

      try (ResultSet resultSet = statement.executeQuery()) {
        if (!resultSet.next()) {
          return Optional.empty();
        }

        final List<String> countries = getCountries(movieId.value());
        return Optional.of(MoviePersistenceMapper.fromResultSetToModel(resultSet, countries));
      }
    } catch (final SQLException exception) {
      throw PersistenceException.becauseMovieFindByIdFailed(movieId.value(), exception);
    }
  }

  @Override
  public List<MovieModel> getAll() {
    try {
      final Map<String, List<String>> countriesByMovie = getAllCountries();

      try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
          ResultSet resultSet = statement.executeQuery()) {
        return MoviePersistenceMapper.fromResultSetToModelList(resultSet, countriesByMovie);
      }
    } catch (final SQLException exception) {
      throw PersistenceException.becauseMovieFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final MovieId movieId) {
    try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, movieId.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseMovieDeleteFailed(movieId.value(), exception);
    }
  }

  private void insertMovie(final MoviePersistenceDto dto) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.distributionTitle());
      statement.setString(3, dto.originalTitle());
      statement.setString(4, dto.genre());
      statement.setString(5, dto.originalLanguage());
      statement.setBoolean(6, dto.spanishSubtitles());
      statement.setInt(7, dto.productionYear());
      statement.setString(8, dto.officialWebsite());
      statement.setInt(9, dto.durationMinutes());
      statement.setString(10, dto.ageRating());
      statement.setString(11, dto.releaseDateSantiago());
      statement.setString(12, dto.summary());
      statement.executeUpdate();
    }
  }

  private void updateMovie(final MoviePersistenceDto dto) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.distributionTitle());
      statement.setString(2, dto.originalTitle());
      statement.setString(3, dto.genre());
      statement.setString(4, dto.originalLanguage());
      statement.setBoolean(5, dto.spanishSubtitles());
      statement.setInt(6, dto.productionYear());
      statement.setString(7, dto.officialWebsite());
      statement.setInt(8, dto.durationMinutes());
      statement.setString(9, dto.ageRating());
      statement.setString(10, dto.releaseDateSantiago());
      statement.setString(11, dto.summary());
      statement.setString(12, dto.id());
      statement.executeUpdate();
    }
  }

  private void insertCountries(final MoviePersistenceDto dto) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COUNTRY)) {
      for (final String country : dto.countriesOfOrigin()) {
        statement.setString(1, dto.id());
        statement.setString(2, country);
        statement.addBatch();
      }
      statement.executeBatch();
    }
  }

  private void deleteCountries(final String movieId) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COUNTRIES)) {
      statement.setString(1, movieId);
      statement.executeUpdate();
    }
  }

  private List<String> getCountries(final String movieId) throws SQLException {
    final List<String> countries = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNTRIES_BY_ID)) {
      statement.setString(1, movieId);

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          countries.add(resultSet.getString("country"));
        }
      }
    }

    return countries;
  }

  private Map<String, List<String>> getAllCountries() throws SQLException {
    final Map<String, List<String>> countriesByMovie = new HashMap<>();

    try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNTRIES);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        countriesByMovie
            .computeIfAbsent(resultSet.getString("movie_id"), key -> new ArrayList<>())
            .add(resultSet.getString("country"));
      }
    }

    return countriesByMovie;
  }

  private boolean exists(final String movieId) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQL_EXISTS)) {
      statement.setString(1, movieId);

      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next();
      }
    }
  }

  private MovieModel findByIdOrFail(final MovieId movieId) {
    return getById(movieId)
        .orElseThrow(() -> MovieNotFoundException.becauseIdWasNotFound(movieId.value()));
  }

  private void executeTransaction(final SqlOperation operation) throws SQLException {
    final boolean previousAutoCommit = connection.getAutoCommit();

    try {
      connection.setAutoCommit(false);
      operation.execute();
      connection.commit();
    } catch (final SQLException | RuntimeException exception) {
      connection.rollback();
      throw exception;
    } finally {
      connection.setAutoCommit(previousAutoCommit);
    }
  }

  @FunctionalInterface
  private interface SqlOperation {
    void execute() throws SQLException;
  }
}
