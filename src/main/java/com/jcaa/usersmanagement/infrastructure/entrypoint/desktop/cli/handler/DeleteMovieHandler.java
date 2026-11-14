
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.MovieController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteMovieHandler implements OperationHandler {

  private final MovieController movieController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final String id = console.readRequired("Movie ID to delete: ");

    movieController.deleteMovie(new DeleteMovieCommand(id));

    console.println("\n  Movie deleted successfully.");
  }
}