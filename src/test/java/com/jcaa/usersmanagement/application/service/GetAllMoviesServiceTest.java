
package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllMoviesPort;
import com.jcaa.usersmanagement.domain.enums.AgeRating;
import com.jcaa.usersmanagement.domain.model.MovieModel;
import com.jcaa.usersmanagement.domain.valueobject.MovieId;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllMoviesService")
@ExtendWith(MockitoExtension.class)
class GetAllMoviesServiceTest {

  @Mock private GetAllMoviesPort getAllMoviesPort;

  private GetAllMoviesService service;

  @BeforeEach
  void setUp() {
    service = new GetAllMoviesService(getAllMoviesPort);
  }

  @Test
  @DisplayName("execute() devuelve todas las películas")
  void shouldReturnAllMovies() {
    final List<MovieModel> movies =
        List.of(
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
                "Una aventura espacial."),
            MovieModel.create(
                new MovieId("MOV-02"),
                "El origen",
                "Inception",
                "Ciencia ficción",
                "Inglés",
                true,
                List.of("Estados Unidos"),
                2010,
                "https://www.example.com",
                148,
                AgeRating.PLUS_15,
                LocalDate.of(2024, 1, 1),
                "Un ladrón que roba secretos mediante sueños."));

    when(getAllMoviesPort.getAll()).thenReturn(movies);

    final List<MovieModel> result = service.execute();

    assertEquals(2, result.size());
    assertEquals("MOV-01", result.get(0).getId().value());
    assertEquals("MOV-02", result.get(1).getId().value());

    verify(getAllMoviesPort).getAll();
  }

  @Test
  @DisplayName("execute() devuelve una lista vacía cuando no hay películas")
  void shouldReturnEmptyListWhenThereAreNoMovies() {
    when(getAllMoviesPort.getAll()).thenReturn(List.of());

    final List<MovieModel> result = service.execute();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(getAllMoviesPort).getAll();
  }
}