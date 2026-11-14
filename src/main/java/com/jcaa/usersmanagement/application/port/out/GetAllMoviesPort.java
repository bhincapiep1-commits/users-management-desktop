package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.MovieModel;
import java.util.List;

public interface GetAllMoviesPort {
  List<MovieModel> getAll();
}