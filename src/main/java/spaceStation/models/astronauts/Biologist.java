package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut{
    private static final int INITIAL_OXYGEN_UNITS_BIOLOGIST = 70;
    private static final int OXYGEN_UNITS_DECREASED_BY_BREATHING_BIOLOGIST = 5;
    private String name;


    public Biologist(String name) {
        super(name, INITIAL_OXYGEN_UNITS_BIOLOGIST);
    }

    @Override
    public void breath() {
        setOxygen(this.getOxygen() - OXYGEN_UNITS_DECREASED_BY_BREATHING_BIOLOGIST);
    }
}
