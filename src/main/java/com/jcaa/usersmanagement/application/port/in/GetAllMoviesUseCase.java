package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.MovieModel;
import java.util.List;

public interface GetAllMoviesUseCase {
  List<MovieModel> execute();
}