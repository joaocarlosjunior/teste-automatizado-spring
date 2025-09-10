package com.joaocarlos.swplanetapi.services;

import com.joaocarlos.swplanetapi.builder.QueryBuilder;
import com.joaocarlos.swplanetapi.domain.Planet;
import com.joaocarlos.swplanetapi.repositories.PlanetRespository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
    private final PlanetRespository planetRespository;

    public PlanetService(PlanetRespository planetRespository) {
        this.planetRespository = planetRespository;
    }

    public Planet createPlanet(Planet planet) {
        return this.planetRespository.save(planet);
    }

    public Optional<Planet> getPlanetById(Long id) {
        return this.planetRespository.findById(id);
    }

    public Optional<Planet> getPlanetByName(String name) {
        return this.planetRespository.findByName(name);
    }

    public List<Planet> filterByClimateOrTerrain(String climate, String terrain) {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));
        return planetRespository.findAll(query);
    }

    public void delete(Long id) {
        planetRespository.deleteById(id);
    }
}
