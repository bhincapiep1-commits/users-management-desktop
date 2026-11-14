
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.MovieResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.MovieController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindMovieByIdHandler implements OperationHandler {

  private final MovieController movieController;
  private final ConsoleIO console;
  private final MovieResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Movie ID: ");

    printer.print(movieController.findMovieById(new GetMovieByIdQuery(id)));
  }
}