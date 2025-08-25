package com.joaocarlos.api_planetas_start_wars.repositories;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRespository extends JpaRepository<Planet, Long> {
}
