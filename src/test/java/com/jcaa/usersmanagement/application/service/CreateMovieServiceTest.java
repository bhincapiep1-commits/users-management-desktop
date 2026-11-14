
package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveMoviePort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateMovieCommand;
import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.exception.MovieAlreadyExistsException;
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

@DisplayName("CreateMovieService")
@ExtendWith(MockitoExtension.class)
class CreateMovieServiceTest {

  @Mock private SaveMoviePort saveMoviePort;
  @Mock private GetMovieByIdPort getMovieByIdPort;

  private CreateMovieService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateMovieService(
              saveMoviePort,
              getMovieByIdPort,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda la película cuando el identificador no existe")
  void shouldSaveMovieWhenIdIsNew() {
    final CreateMovieCommand command =
        new CreateMovieCommand(
            "MOV-01",
            "Interestelar",
            "Interstellar",
            "Ciencia ficción",
            "Inglés",
            true,
            List.of("Estados Unidos", "Reino Unido"),
            2014,
            "https://www.example.com",
            169,
            "PLUS_9",
            LocalDate.of(2024, 1, 1),
            "Una aventura espacial.");

    final MovieModel savedMovie =
        MovieModel.create(
            new MovieId("MOV-01"),
            "Interestelar",
            "Interstellar",
            "Ciencia ficción",
            "Inglés",
            true,
            List.of("Estados Unidos", "Reino Unido"),
            2014,
            "https://www.example.com",
            169,
            AgeRating.PLUS_9,
            LocalDate.of(2024, 1, 1),
            "Una aventura espacial.");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.empty());
    when(saveMoviePort.save(any())).thenReturn(savedMovie);

    final MovieModel result = service.execute(command);

    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals("MOV-01", result.getId().value()),
        () -> assertEquals("Interestelar", result.getDistributionTitle()));

    verify(saveMoviePort).save(any(MovieModel.class));
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el identificador ya existe")
  void shouldThrowWhenMovieAlreadyExists() {
    final CreateMovieCommand command =
        new CreateMovieCommand(
            "MOV-01",
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
            "Una aventura espacial.");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.of(existingMovie));

    assertThrows(MovieAlreadyExistsException.class, () -> service.execute(command));

    verify(saveMoviePort, never()).save(any());
  }

  @Test
  @DisplayName("execute() lanza excepción cuando los datos son inválidos")
  void shouldThrowWhenCommandIsInvalid() {
    final CreateMovieCommand command =
        new CreateMovieCommand(
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

    verifyNoInteractions(saveMoviePort, getMovieByIdPort);
  }
}