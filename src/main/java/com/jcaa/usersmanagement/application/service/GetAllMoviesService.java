
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllMoviesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllMoviesPort;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAllMoviesService implements GetAllMoviesUseCase {

  private final GetAllMoviesPort getAllMoviesPort;

  @Override
  public List<MovieModel> execute() {
    return getAllMoviesPort.getAll();
  }
}