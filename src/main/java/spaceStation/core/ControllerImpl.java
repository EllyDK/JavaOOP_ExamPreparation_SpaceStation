package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;
import spaceStation.repositories.Repository;

import java.util.ArrayDeque;
import java.util.Deque;

public class ControllerImpl implements Controller {
    private Repository<Astronaut> astronautRepository;
    private Repository<Planet> planetRepository;
    private Deque<Planet> exploredPlanets;
    private Deque<Astronaut> suitableAstronauts;
    private Mission mission;

    public ControllerImpl() {
        astronautRepository = new AstronautRepository();
        planetRepository = new PlanetRepository();
        exploredPlanets = new ArrayDeque<>();
        suitableAstronauts = new ArrayDeque<>();
        mission = new MissionImpl();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        if (!type.equals("Biologist") && !type.equals("Geodesist") && !type.equals("Meteorologist")){
            throw new IllegalArgumentException
                    (ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }
        Astronaut astronaut = null;
        switch (type){
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
        }
        astronautRepository.add(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronaut.getName());
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        for (String item : items){
            planet.getItems().add(item);
        }
        planetRepository.add(planet);
        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        if (!astronautRepository.getModels().contains(astronautRepository.findByName(astronautName))){
            throw new IllegalArgumentException
                    (String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }
        String message = String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
        astronautRepository.remove(astronautRepository.findByName(astronautName));
        return message;
    }

    @Override
    public String explorePlanet(String planetName) {
        Planet planetToExplore = planetRepository.findByName(planetName);
        for (Astronaut astronaut : astronautRepository.getModels()){
            if (astronaut.getOxygen() > 60){
                suitableAstronauts.add(astronaut);
            }
        }
        if (suitableAstronauts.isEmpty()){
            throw new IllegalArgumentException
                    (ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        int astronautsCountBeforeMission = suitableAstronauts.size();
        mission.explore(planetToExplore, suitableAstronauts);
        int deadAstronauts = astronautsCountBeforeMission - suitableAstronauts.size();
        exploredPlanets.add(planetToExplore);

        return String.format(ConstantMessages.PLANET_EXPLORED, planetName, deadAstronauts);
    }

    @Override
    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(ConstantMessages.REPORT_PLANET_EXPLORED, exploredPlanets.size()))
                .append(System.lineSeparator());
        stringBuilder.append(ConstantMessages.REPORT_ASTRONAUT_INFO)
                .append(System.lineSeparator());
        for (Astronaut astronaut : suitableAstronauts){
            stringBuilder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, astronaut.getName()))
                    .append(System.lineSeparator());
            stringBuilder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen()))
                    .append(System.lineSeparator());
            if (astronaut.getBag().getItems().size() > 0){
                for (String item : astronaut.getBag().getItems()){
                    stringBuilder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, item))
                            .append(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER);
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append(System.lineSeparator());
            }else {
                stringBuilder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, "none"))
                        .append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}
