
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.MovieResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.MovieController;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateMovieHandler implements OperationHandler {

  private final MovieController movieController;
  private final ConsoleIO console;
  private final MovieResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Movie ID to update: ");
    final String distributionTitle = console.readRequired("Distribution title: ");
    final String originalTitle = console.readRequired("Original title: ");
    final String genre = console.readRequired("Genre: ");
    final String originalLanguage = console.readRequired("Original language: ");

    final String subtitles = console.readRequired("Spanish subtitles? (S/N or TRUE/FALSE): ");
    final String normalizedSubtitles = subtitles.trim().toUpperCase(Locale.ROOT);
    final boolean spanishSubtitles =
        normalizedSubtitles.equals("S")
            || normalizedSubtitles.equals("SI")
            || normalizedSubtitles.equals("YES")
            || normalizedSubtitles.equals("Y")
            || normalizedSubtitles.equals("TRUE");

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

    final UpdateMovieCommand command =
        new UpdateMovieCommand(
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

    final var updated = movieController.updateMovie(command);

    console.println("\n  Movie updated successfully.");
    printer.print(updated);
  }
}