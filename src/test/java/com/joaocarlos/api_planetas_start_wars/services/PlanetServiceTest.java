package com.joaocarlos.api_planetas_start_wars.services;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import com.joaocarlos.api_planetas_start_wars.repositories.PlanetRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.joaocarlos.api_planetas_start_wars.common.PlanetConstants.INVALID_PLANET;
import static com.joaocarlos.api_planetas_start_wars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
}
