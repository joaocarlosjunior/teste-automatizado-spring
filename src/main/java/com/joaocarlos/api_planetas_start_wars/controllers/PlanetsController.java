package com.joaocarlos.api_planetas_start_wars.controllers;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import com.joaocarlos.api_planetas_start_wars.services.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetsController {
    private final PlanetService planetService;

    public PlanetsController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.createPlanet(planet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
        return planetService.getPlanetById(id).map(planet -> ResponseEntity.ok(planet)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> getPlanetByName(@PathVariable String name) {
        return planetService.getPlanetByName(name).map(planet -> ResponseEntity.ok(planet)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> getPlanets() {
        return ResponseEntity.ok(planetService.listPlanets());
    }

}
