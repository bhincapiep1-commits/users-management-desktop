
package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.DeleteMoviePort;
import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteMovieCommand;
import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.exception.MovieNotFoundException;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("DeleteMovieService")
@ExtendWith(MockitoExtension.class)
class DeleteMovieServiceTest {

  @Mock private GetMovieByIdPort getMovieByIdPort;
  @Mock private DeleteMoviePort deleteMoviePort;

  private DeleteMovieService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteMovieService(
              getMovieByIdPort,
              deleteMoviePort,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() elimina la película cuando existe")
  void shouldDeleteMovieWhenItExists() {
    final DeleteMovieCommand command = new DeleteMovieCommand("MOV-01");

    final MovieModel movie =
        MovieModel.create(
            new MovieId("MOV-01"),
            "Interestelar",
            "Interstellar",
            "Ciencia ficción",
            "Inglés",
            true,
            List.of("Estados Unidos"),
            2014,
            "https://www.example.com",
            169,
            AgeRating.PLUS_9,
            LocalDate.of(2024, 1, 1),
            "Una aventura espacial.");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.of(movie));

    assertDoesNotThrow(() -> service.execute(command));

    verify(deleteMoviePort).delete(any(MovieId.class));
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la película no existe")
  void shouldThrowWhenMovieDoesNotExist() {
    final DeleteMovieCommand command = new DeleteMovieCommand("MOV-99");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(MovieNotFoundException.class, () -> service.execute(command));

    verifyNoInteractions(deleteMoviePort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el comando es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    final DeleteMovieCommand command = new DeleteMovieCommand("");

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));

    verifyNoInteractions(getMovieByIdPort, deleteMoviePort);
  }
}