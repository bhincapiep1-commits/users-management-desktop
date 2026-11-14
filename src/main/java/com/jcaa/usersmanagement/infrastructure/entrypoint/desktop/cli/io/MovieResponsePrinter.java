package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.domain.model.MovieModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class MovieResponsePrinter {

  private static final String SEPARATOR = "-".repeat(60);
  private static final String ROW_FORMAT = "  %-22s : %s%n";

  private final ConsoleIO console;

  public void print(final MovieModel movie) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID", movie.getId());
    console.printf(ROW_FORMAT, "Distribution title", movie.getDistributionTitle());
    console.printf(ROW_FORMAT, "Original title", movie.getOriginalTitle());
    console.printf(ROW_FORMAT, "Genre", movie.getGenre());
    console.printf(ROW_FORMAT, "Original language", movie.getOriginalLanguage());
    console.printf(ROW_FORMAT, "Spanish subtitles",
        movie.isSpanishSubtitles() ? "Yes" : "No");
    console.printf(ROW_FORMAT, "Countries",
        String.join(", ", movie.getCountriesOfOrigin()));
    console.printf(ROW_FORMAT, "Production year", movie.getProductionYear());
    console.printf(ROW_FORMAT, "Official website", movie.getOfficialWebsite());
    console.printf(ROW_FORMAT, "Duration (minutes)", movie.getDurationMinutes());
    console.printf(ROW_FORMAT, "Age rating", movie.getAgeRating());
    console.printf(ROW_FORMAT, "Santiago release date", movie.getReleaseDateSantiago());
    console.printf(ROW_FORMAT, "Summary", movie.getSummary());
    console.println(SEPARATOR);
  }

  public void printList(final List<MovieModel> movies) {
    if (movies.isEmpty()) {
      console.println("  No movies found.");
      return;
    }

    console.printf("%n  Total: %d movie(s)%n", movies.size());
    movies.forEach(this::print);
  }
}