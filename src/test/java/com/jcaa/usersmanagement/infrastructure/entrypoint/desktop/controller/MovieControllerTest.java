
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateMovieUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteMovieUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllMoviesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetMovieByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateMovieUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.exception.MovieNotFoundException;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

  @Mock
  private CreateMovieUseCase createMovieUseCase;

  @Mock
  private UpdateMovieUseCase updateMovieUseCase;

  @Mock
  private DeleteMovieUseCase deleteMovieUseCase;

  @Mock
  private GetMovieByIdUseCase getMovieByIdUseCase;

  @Mock
  private GetAllMoviesUseCase getAllMoviesUseCase;

  @InjectMocks
  private MovieController movieController;

  private MovieModel movie;

  @BeforeEach
  void setUp() {
    movie = MovieModel.create(
        new MovieId("MOV-001"),
        "Interestelar",
        "Interstellar",
        "Ciencia ficción",
        "Inglés",
        true,
        List.of("Estados Unidos"),
        2014,
        "https://www.warnerbros.com/",
        169,
        AgeRating.PLUS_9,
        LocalDate.of(2014, 11, 6),
        "Una misión espacial para salvar a la humanidad.");
  }

  @Test
  void listAllMovies_returnsMovies() {
    when(getAllMoviesUseCase.execute()).thenReturn(List.of(movie));

    List<MovieModel> result = movieController.listAllMovies();

    assertEquals(1, result.size());
    assertEquals(movie, result.get(0));
    verify(getAllMoviesUseCase).execute();
  }

  @Test
  void listAllMovies_returnsEmptyList() {
    when(getAllMoviesUseCase.execute()).thenReturn(List.of());

    List<MovieModel> result = movieController.listAllMovies();

    assertEquals(0, result.size());
    verify(getAllMoviesUseCase).execute();
  }

  @Test
  void findMovieById_delegatesQuery() {
    GetMovieByIdQuery query = new GetMovieByIdQuery("MOV-001");
    when(getMovieByIdUseCase.execute(query)).thenReturn(movie);

    MovieModel result = movieController.findMovieById(query);

    assertEquals(movie, result);
    verify(getMovieByIdUseCase).execute(query);
  }

  @Test
  void findMovieById_propagatesNotFoundException() {
    GetMovieByIdQuery query = new GetMovieByIdQuery("MOV-999");

    when(getMovieByIdUseCase.execute(query))
        .thenThrow(MovieNotFoundException.becauseIdWasNotFound("MOV-999"));

    assertThrows(
        MovieNotFoundException.class,
        () -> movieController.findMovieById(query));
  }

  @Test
  void createMovie_delegatesCommand() {
    CreateMovieCommand command =
        org.mockito.Mockito.mock(CreateMovieCommand.class);

    when(createMovieUseCase.execute(command)).thenReturn(movie);

    MovieModel result = movieController.createMovie(command);

    assertEquals(movie, result);
    verify(createMovieUseCase).execute(command);
  }

  @Test
  void updateMovie_delegatesCommand() {
    UpdateMovieCommand command =
        org.mockito.Mockito.mock(UpdateMovieCommand.class);

    when(updateMovieUseCase.execute(command)).thenReturn(movie);

    MovieModel result = movieController.updateMovie(command);

    assertEquals(movie, result);
    verify(updateMovieUseCase).execute(command);
  }

  @Test
  void deleteMovie_delegatesCommand() {
    DeleteMovieCommand command =
        org.mockito.Mockito.mock(DeleteMovieCommand.class);

    movieController.deleteMovie(command);

    verify(deleteMovieUseCase).execute(command);
  }

  @Test
  void deleteMovie_propagatesNotFoundException() {
    DeleteMovieCommand command =
        org.mockito.Mockito.mock(DeleteMovieCommand.class);

    doThrow(MovieNotFoundException.becauseIdWasNotFound("MOV-999"))
        .when(deleteMovieUseCase).execute(command);

    assertThrows(
        MovieNotFoundException.class,
        () -> movieController.deleteMovie(command));
  }
}