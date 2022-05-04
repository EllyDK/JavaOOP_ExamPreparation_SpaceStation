package spaceStation.models.astronauts;

public class Geodesist extends BaseAstronaut{
    private static final int INITIAL_OXYGEN_UNITS_GEODESIST = 50;
    private String name;

    public Geodesist(String name) {
        super(name, INITIAL_OXYGEN_UNITS_GEODESIST);
    }
}
