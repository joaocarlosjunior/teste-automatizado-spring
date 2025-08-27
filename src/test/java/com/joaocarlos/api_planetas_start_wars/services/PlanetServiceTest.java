package com.joaocarlos.api_planetas_start_wars.services;

import com.joaocarlos.api_planetas_start_wars.builder.QueryBuilder;
import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import com.joaocarlos.api_planetas_start_wars.repositories.PlanetRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.joaocarlos.api_planetas_start_wars.common.PlanetConstants.INVALID_PLANET;
import static com.joaocarlos.api_planetas_start_wars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRespository planetRespository;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        //AAA
        // Arange
        when(planetRespository.save(PLANET)).thenReturn(PLANET);

        // Act
        Planet sut = planetService.createPlanet(PLANET);

        // Assert
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRespository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.createPlanet(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanetById_ByExistingId_ReturnsPlanet() {
        when(planetRespository.findById(1L)).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getPlanetById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanetById_ByUnexistingId_ReturnsEmpty() {
        when(planetRespository.findById(1L)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getPlanetById(1L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanetByName_ByExistingName_ReturnsPlanet() {
        when(planetRespository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getPlanetByName("name");

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanetByName_ByUnexistingName_ReturnsEmpty() {
        when(planetRespository.findByName(PLANET.getName())).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getPlanetByName("name");

        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRespository.findAll(query)).thenReturn(List.of(PLANET));

        List<Planet> sut = planetService.filterByClimateOrTerrain("climate", "terrain");

        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnsEmptyList(){
        when(planetRespository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.filterByClimateOrTerrain(PLANET.getClimate(), PLANET.getTerrain());

        assertThat(sut).isEmpty();
    }

    @Test
    public void deletePlanet_WithExistingId_DoesNotThrowAnyException() {
        assertThatCode(() -> planetService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deletePlanet_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRespository).deleteById(99L);
        assertThatThrownBy(() -> planetService.delete(99L)).isInstanceOf(RuntimeException.class);
    }
}
