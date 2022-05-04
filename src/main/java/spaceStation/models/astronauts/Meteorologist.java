package spaceStation.models.astronauts;

public class Meteorologist extends BaseAstronaut{
    private static final int INITIAL_OXYGEN_UNITS_METEOROLOGIST = 90;
    private String name;

    public Meteorologist(String name) {
        super(name, INITIAL_OXYGEN_UNITS_METEOROLOGIST);
    }
}
