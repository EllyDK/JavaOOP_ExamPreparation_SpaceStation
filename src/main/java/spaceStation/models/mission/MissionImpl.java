package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class MissionImpl implements Mission{

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        Deque<Astronaut> astronautsDeque = new ArrayDeque<>(astronauts);
        Astronaut currentAstronaut = astronautsDeque.poll();
        Deque<String> planetItems = new ArrayDeque<>(planet.getItems());

        while (!astronautsDeque.isEmpty() && !planetItems.isEmpty()){
            if (currentAstronaut.getOxygen() < 0){
                currentAstronaut = astronautsDeque.poll();
            }
            currentAstronaut.getBag().getItems().add(planetItems.poll());
            currentAstronaut.breath();
        }

    }
}
