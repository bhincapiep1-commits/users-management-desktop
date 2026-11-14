
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.MovieResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.MovieController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListMoviesHandler implements OperationHandler {

  private final MovieController movieController;
  private final MovieResponsePrinter printer;

  @Override
  public void handle() {
    printer.printList(movieController.listAllMovies());
  }
}