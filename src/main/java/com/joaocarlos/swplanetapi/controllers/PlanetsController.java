package com.joaocarlos.swplanetapi.controllers;

import com.joaocarlos.swplanetapi.domain.Planet;
import com.joaocarlos.swplanetapi.services.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Planet> create(@RequestBody @Validated Planet planet) {
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

    @GetMapping()
    public ResponseEntity<List<Planet>> filterPlanetsByTerrainOrClimate(
            @RequestParam(required = false) String terrain,
            @RequestParam(required = false) String climate
    ) {
        return ResponseEntity.ok(this.planetService.filterByClimateOrTerrain(climate, terrain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanetById(@PathVariable Long id) {
        this.planetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
