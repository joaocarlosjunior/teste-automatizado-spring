package com.joaocarlos.api_planetas_start_wars.services;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import com.joaocarlos.api_planetas_start_wars.repositories.PlanetRespository;
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

    public List<Planet> listPlanets() {
        return this.planetRespository.findAll();
    }
}
