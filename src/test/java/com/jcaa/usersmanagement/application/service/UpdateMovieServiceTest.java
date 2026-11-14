
package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateMoviePort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateMovieCommand;
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

@DisplayName("UpdateMovieService")
@ExtendWith(MockitoExtension.class)
class UpdateMovieServiceTest {

  @Mock private GetMovieByIdPort getMovieByIdPort;
  @Mock private UpdateMoviePort updateMoviePort;

  private UpdateMovieService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateMovieService(
              getMovieByIdPort,
              updateMoviePort,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza la película cuando existe")
  void shouldUpdateMovieWhenItExists() {
    final UpdateMovieCommand command =
        new UpdateMovieCommand(
            "MOV-01",
            "Interestelar Actualizada",
            "Interstellar",
            "Ciencia ficción",
            "Inglés",
            true,
            List.of("Estados Unidos"),
            2014,
            "https://www.example.com",
            169,
            "PLUS_9",
            LocalDate.of(2024, 1, 1),
            "Resumen actualizado.");

    final MovieModel existingMovie =
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
            "Resumen original.");

    final MovieModel updatedMovie =
        MovieModel.create(
            new MovieId("MOV-01"),
            "Interestelar Actualizada",
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
            "Resumen actualizado.");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.of(existingMovie));
    when(updateMoviePort.update(any())).thenReturn(updatedMovie);

    final MovieModel result = service.execute(command);

    assertNotNull(result);
    assertEquals("Interestelar Actualizada", result.getDistributionTitle());
    verify(updateMoviePort).update(any(MovieModel.class));
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la película no existe")
  void shouldThrowWhenMovieDoesNotExist() {
    final UpdateMovieCommand command =
        new UpdateMovieCommand(
            "MOV-99",
            "Interestelar",
            "Interstellar",
            "Ciencia ficción",
            "Inglés",
            true,
            List.of("Estados Unidos"),
            2014,
            "https://www.example.com",
            169,
            "PLUS_9",
            LocalDate.of(2024, 1, 1),
            "Una aventura espacial.");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(MovieNotFoundException.class, () -> service.execute(command));
    verifyNoInteractions(updateMoviePort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando los datos son inválidos")
  void shouldThrowWhenCommandIsInvalid() {
    final UpdateMovieCommand command =
        new UpdateMovieCommand(
            "",
            "",
            "",
            "",
            "",
            false,
            List.of(),
            0,
            "",
            0,
            "PLUS_9",
            null,
            "");

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(getMovieByIdPort, updateMoviePort);
  }
}