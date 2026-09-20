package br.com.pedro.swapi.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.pedro.swapi.exception.FilmNotFoundException;
import br.com.pedro.swapi.exception.GlobalExceptionHandler;
import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.service.FilmService;
import tools.jackson.databind.ObjectMapper;

class FilmControllerTest {

    private MockMvc mockMvc;

    private FilmService filmService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        filmService = mock(FilmService.class);

        FilmController controller = new FilmController(filmService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldListAllFilms() throws Exception {

        Film film = createFilm();

        when(filmService.findAll())
                .thenReturn(List.of(film));

        mockMvc.perform(
                get("/api/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].episodeId").value(4))
                .andExpect(jsonPath("$[0].title").value("A New Hope"))
                .andExpect(jsonPath("$[0].version").value(1));

        verify(filmService).findAll();
    }

    @Test
    void shouldFindFilmById() throws Exception {

        Film film = createFilm();

        when(filmService.findById(4))
                .thenReturn(film);

        mockMvc.perform(
                get("/api/films/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.episodeId").value(4))
                .andExpect(jsonPath("$.title").value("A New Hope"))
                .andExpect(jsonPath("$.description")
                        .value("Descrição original"))
                .andExpect(jsonPath("$.version").value(1));

        verify(filmService).findById(4);
    }

    @Test
    void shouldUpdateFilmDescription() throws Exception {

        Film film = createFilm();

        film.setDescription("Nova descrição");
        film.setVersion(2);

        when(filmService.updateDescription(
                4,
                "Nova descrição")).thenReturn(film);

        String requestBody = """
                {
                    "description": "Nova descrição"
                }
                """;

        mockMvc.perform(
                patch("/api/films/4/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.episodeId").value(4))
                .andExpect(jsonPath("$.description")
                        .value("Nova descrição"))
                .andExpect(jsonPath("$.version").value(2));

        verify(filmService)
                .updateDescription(
                        4,
                        "Nova descrição");
    }

    private Film createFilm() {

        return new Film(
                4,
                "A New Hope",
                "Descrição original",
                "George Lucas",
                "Gary Kurtz, Rick McCallum",
                "1977-05-25",
                1);
    }

    @Test
    void shouldReturnBadRequestWhenDescriptionIsBlank() throws Exception {

        String requestBody = """
                {
                    "description": ""
                }
                """;

        mockMvc.perform(
                patch("/api/films/4/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(filmService);
    }

    @Test
    void shouldReturnNotFoundWhenFilmDoesNotExist() throws Exception {

        when(filmService.findById(9999))
                .thenThrow(new FilmNotFoundException(9999));

        mockMvc.perform(
                get("/api/films/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Filme não encontrado para o episodeId: 9999"));

        verify(filmService).findById(9999);
    }

    @Test
    void shouldReturnBadRequestWhenDescriptionExceeds1000Characters() throws Exception {

        String description = "a".repeat(1001);

        String requestBody = """
                {
                    "description": "%s"
                }
                """.formatted(description);

        mockMvc.perform(
                patch("/api/films/4/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(filmService);
    }
}