package com.joaocarlos.swplanetapi.repositories;

import com.joaocarlos.swplanetapi.builder.QueryBuilder;
import com.joaocarlos.swplanetapi.domain.Planet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.joaocarlos.swplanetapi.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PlanetRepositoryTest {
    @Autowired
    private PlanetRespository planetRespository;
    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet planet = planetRespository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(planet.getName());
        assertThat(sut.getClimate()).isEqualTo(planet.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(planet.getTerrain());
    }

    @ParameterizedTest
    @MethodSource("providesInvalidPlanets")
    public void createPlanet_WithInvalidData_ThrowsException(Planet planet) {
        assertThatThrownBy(() -> planetRespository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    private static Stream<Arguments> providesInvalidPlanets() {
        return Stream.of(
                Arguments.of(new Planet(null, "climate", "terrain")),
                Arguments.of(new Planet("name", null, "terrain")),
                Arguments.of(new Planet(null, null, "terrain")),
                Arguments.of(new Planet(null, "climate", null)),
                Arguments.of(new Planet(null, null, null)),
                Arguments.of(new Planet("", "", ""))
        );
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRespository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> sut = planetRespository.findById(planet.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get().getName()).isEqualTo(planet.getName());
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        Optional<Planet> sut = planetRespository.findById(99L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> sut = planetRespository.findByName(planet.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        Optional<Planet> sut = planetRespository.findByName("teste");

        assertThat(sut).isEmpty();
    }

    @Sql(scripts = "/sql/import_planets.sql")
    @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(),TATOOINE.getTerrain()));

        List<Planet> responseWithoutFilters = planetRespository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = planetRespository.findAll(queryWithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);
        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithFilters.get(0).getName()).isEqualTo(TATOOINE.getName());
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet("Teste", "Teste"));

        List<Planet> sut = planetRespository.findAll(query);

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_ByExistingId_RemovesPlanetFromDatabase(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRespository.deleteById(planet.getId());

        Planet removePlanet = testEntityManager.find(Planet.class, planet.getId());
        assertThat(removePlanet).isNull();
    }
}
