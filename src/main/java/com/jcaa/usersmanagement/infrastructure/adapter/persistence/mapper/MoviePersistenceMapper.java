package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.MoviePersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.MovieEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MoviePersistenceMapper {

  public MoviePersistenceDto fromModelToDto(final MovieModel movie) {
    return new MoviePersistenceDto(
        movie.getId().value(),
        movie.getDistributionTitle(),
        movie.getOriginalTitle(),
        movie.getGenre(),
        movie.getOriginalLanguage(),
        movie.isSpanishSubtitles(),
        movie.getCountriesOfOrigin(),
        movie.getProductionYear(),
        movie.getOfficialWebsite(),
        movie.getDurationMinutes(),
        movie.getAgeRating().name(),
        movie.getReleaseDateSantiago().toString(),
        movie.getSummary());
  }

  public MovieEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new MovieEntity(
        resultSet.getString("id"),
        resultSet.getString("distribution_title"),
        resultSet.getString("original_title"),
        resultSet.getString("genre"),
        resultSet.getString("original_language"),
        resultSet.getBoolean("spanish_subtitles"),
        resultSet.getInt("production_year"),
        resultSet.getString("official_website"),
        resultSet.getInt("duration_minutes"),
        resultSet.getString("age_rating"),
        resultSet.getString("release_date_santiago"),
        resultSet.getString("summary"));
  }

  public MovieModel fromEntityToModel(
      final MovieEntity entity, final List<String> countriesOfOrigin) {
    return MovieModel.create(
        new MovieId(entity.id()),
        entity.distributionTitle(),
        entity.originalTitle(),
        entity.genre(),
        entity.originalLanguage(),
        entity.spanishSubtitles(),
        countriesOfOrigin,
        entity.productionYear(),
        entity.officialWebsite(),
        entity.durationMinutes(),
        AgeRating.fromString(entity.ageRating()),
        java.time.LocalDate.parse(entity.releaseDateSantiago()),
        entity.summary());
  }

  public MovieModel fromResultSetToModel(
      final ResultSet resultSet, final List<String> countriesOfOrigin) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet), countriesOfOrigin);
  }

  public List<MovieModel> fromResultSetToModelList(
      final ResultSet resultSet,
      final java.util.Map<String, List<String>> countriesByMovie) throws SQLException {
    final List<MovieModel> movies = new ArrayList<>();
    while (resultSet.next()) {
      final MovieEntity entity = fromResultSetToEntity(resultSet);
      movies.add(fromEntityToModel(
          entity,
          countriesByMovie.getOrDefault(entity.id(), List.of())));
    }
    return movies;
  }
}
