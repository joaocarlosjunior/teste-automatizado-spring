package com.joaocarlos.api_planetas_start_wars.builder;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    public static Example<Planet> makeQuery(Planet planet){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(planet, exampleMatcher);
    }
}
