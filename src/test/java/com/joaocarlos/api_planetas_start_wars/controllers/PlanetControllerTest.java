package com.joaocarlos.api_planetas_start_wars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaocarlos.api_planetas_start_wars.services.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.joaocarlos.api_planetas_start_wars.common.PlanetConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanetsController.class)
public class PlanetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        // Arrange
        when(planetService.createPlanet(PLANET)).thenReturn(PLANET);

        // Act e Assert
        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsUnprocessableEntity() throws Exception {
        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(INVALID_PLANET)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws Exception {
        when(planetService.createPlanet(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {
        when(planetService.getPlanetById(1L)).thenReturn(Optional.of(PLANET));

        mockMvc.perform(get("/planets/{id}", 1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() throws Exception {
        when(planetService.getPlanetById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/planets/{id}", 99L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception {
        when(planetService.getPlanetByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        mockMvc.perform(get("/planets/name/{name}", PLANET.getName()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() throws Exception {
        when(planetService.getPlanetByName(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/planets/name/{name}", "teste").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        when(planetService.filterByClimateOrTerrain(null, null)).thenReturn(PLANETS);
        when(planetService.filterByClimateOrTerrain(TATOOINE.getClimate(), TATOOINE.getTerrain())).thenReturn(List.of(TATOOINE));

        mockMvc.perform(get("/planets")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));

        mockMvc.perform(get("/planets").param("terrain", TATOOINE.getTerrain()).param("climate", TATOOINE.getClimate()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]").value(TATOOINE));
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {
        when(planetService.filterByClimateOrTerrain("Clima", "Terra")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/planets/filter").param("terrain", "Terra").param("climate", "Clima").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void removePlanet_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/planets/{id}", 1L)).andExpect(status().isNoContent());
    }

    @Test
    public void removePlanet_WithUnexistingId_ReturnsNoContent() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(planetService).delete(1L);

        mockMvc.perform(delete("/planets/{id}", 1L)).andExpect(status().isNotFound());
    }
}
