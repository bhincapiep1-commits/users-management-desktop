
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.MovieResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.MovieController;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateMovieHandler implements OperationHandler {

  private final MovieController movieController;
  private final ConsoleIO console;
  private final MovieResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Movie ID: ");
    final String distributionTitle = console.readRequired("Distribution title: ");
    final String originalTitle = console.readRequired("Original title: ");
    final String genre = console.readRequired("Genre: ");
    final String originalLanguage = console.readRequired("Original language: ");

    final String subtitles = console.readRequired("Spanish subtitles? (S/N): ");
    final boolean spanishSubtitles =
        subtitles.equalsIgnoreCase("S")
            || subtitles.equalsIgnoreCase("SI")
            || subtitles.equalsIgnoreCase("YES");

    final String countriesInput =
        console.readRequired("Countries of origin (separated by commas): ");

    final List<String> countriesOfOrigin =
        Arrays.stream(countriesInput.split(","))
            .map(String::trim)
            .filter(country -> !country.isEmpty())
            .toList();

    final int productionYear = console.readInt("Production year: ");
    final String officialWebsite = console.readRequired("Official website: ");
    final int durationMinutes = console.readInt("Duration in minutes: ");

    console.println("Age ratings: ALL, PLUS_9, PLUS_15, PLUS_18");
    final String ageRating = console.readRequired("Age rating: ");

    final LocalDate releaseDateSantiago =
        LocalDate.parse(console.readRequired("Santiago release date (YYYY-MM-DD): "));

    final String summary = console.readRequired("Summary: ");

    final CreateMovieCommand command =
        new CreateMovieCommand(
            id,
            distributionTitle,
            originalTitle,
            genre,
            originalLanguage,
            spanishSubtitles,
            countriesOfOrigin,
            productionYear,
            officialWebsite,
            durationMinutes,
            ageRating,
            releaseDateSantiago,
            summary);

    final var created = movieController.createMovie(command);

    console.println("\n  Movie created successfully.");
    printer.print(created);
  }
}