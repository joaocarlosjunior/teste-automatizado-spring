package com.joaocarlos.api_planetas_start_wars.common;

import com.joaocarlos.api_planetas_start_wars.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
}
