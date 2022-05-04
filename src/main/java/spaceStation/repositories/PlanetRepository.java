package spaceStation.repositories;

import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlanetRepository implements Repository<Planet>{
    private Map<String, Planet> planets;

    public PlanetRepository() {
        this.planets = new LinkedHashMap<>();
    }

    @Override
    public Collection<Planet> getModels() {
        return planets.values();
    }

    @Override
    public void add(Planet planet) {
        if (!planets.containsKey(planet.getName())){
            planets.put(planet.getName(), planet);
        }
    }

    @Override
    public boolean remove(Planet planet) {
        return planets.remove(planet.getName()) != null;
    }

    @Override
    public Planet findByName(String name) {
        return planets.get(name);
    }
}
