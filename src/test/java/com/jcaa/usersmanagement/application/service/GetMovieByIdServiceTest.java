
package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetMovieByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetMovieByIdQuery;
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

@DisplayName("GetMovieByIdService")
@ExtendWith(MockitoExtension.class)
class GetMovieByIdServiceTest {

  @Mock private GetMovieByIdPort getMovieByIdPort;

  private GetMovieByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new GetMovieByIdService(getMovieByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() devuelve la película cuando existe")
  void shouldReturnMovieWhenItExists() {
    final GetMovieByIdQuery query = new GetMovieByIdQuery("MOV-01");

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

    final MovieModel result = service.execute(query);

    assertNotNull(result);
    assertEquals("MOV-01", result.getId().value());
    assertEquals("Interestelar", result.getDistributionTitle());

    verify(getMovieByIdPort).getById(any());
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la película no existe")
  void shouldThrowWhenMovieDoesNotExist() {
    final GetMovieByIdQuery query = new GetMovieByIdQuery("MOV-99");

    when(getMovieByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(MovieNotFoundException.class, () -> service.execute(query));
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la consulta es inválida")
  void shouldThrowWhenQueryIsInvalid() {
    final GetMovieByIdQuery query = new GetMovieByIdQuery("");

    assertThrows(ConstraintViolationException.class, () -> service.execute(query));

    verifyNoInteractions(getMovieByIdPort);
  }
}