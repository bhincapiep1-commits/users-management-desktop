package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieApplicationMapper {

  public MovieModel fromCreateCommandToModel(final CreateMovieCommand command) {
    return MovieModel.create(
        new MovieId(command.id()),
        command.distributionTitle(),
        command.originalTitle(),
        command.genre(),
        command.originalLanguage(),
        command.spanishSubtitles(),
        command.countriesOfOrigin(),
        command.productionYear(),
        command.officialWebsite(),
        command.durationMinutes(),
        AgeRating.fromString(command.ageRating()),
        command.releaseDateSantiago(),
        command.summary());
  }

  public MovieModel fromUpdateCommandToModel(final UpdateMovieCommand command) {
    return MovieModel.create(
        new MovieId(command.id()),
        command.distributionTitle(),
        command.originalTitle(),
        command.genre(),
        command.originalLanguage(),
        command.spanishSubtitles(),
        command.countriesOfOrigin(),
        command.productionYear(),
        command.officialWebsite(),
        command.durationMinutes(),
        AgeRating.fromString(command.ageRating()),
        command.releaseDateSantiago(),
        command.summary());
  }

  public MovieId fromGetMovieByIdQueryToMovieId(final GetMovieByIdQuery query) {
    return new MovieId(query.id());
  }

  public MovieId fromDeleteCommandToMovieId(final DeleteMovieCommand command) {
    return new MovieId(command.id());
  }
}