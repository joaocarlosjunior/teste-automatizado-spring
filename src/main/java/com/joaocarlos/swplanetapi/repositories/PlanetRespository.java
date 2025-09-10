package com.joaocarlos.swplanetapi.repositories;

import com.joaocarlos.swplanetapi.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRespository extends JpaRepository<Planet, Long> {
    Optional<Planet> findByName(String name);
}
