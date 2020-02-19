package rockets;


import solarsystem.CosmicBody;

import java.util.ArrayList;

public class HotSpotRocket extends StandardRocket implements Runnable {


    private static final int FUEL_CONSUME_BY_HSR_FROM_HOT_SPOTS = 50;
    private static final int FUEL_CONSUME_BY_HSR = 25;

    public HotSpotRocket(String name, CosmicBody current) {
        super(name, current);
    }

    @Override
    public double getMaximumPacksWeight() {
        return MAX_WEIGHT_HOTSPOT_ROCKET;
    }

    @Override
    public ArrayList<String> getNotAllowedPlanetsNames() {
        return NOT_ALLOWED_PLANETS_HOTSPOT_ROCKET;
    }


    @Override
    public void consumeFuel() {

        fuel -= NOT_ALLOWED_PLANETS_STANDARD_ROCKET.contains(currentCosmicBody.getName()) ?
                FUEL_CONSUME_BY_HSR_FROM_HOT_SPOTS : FUEL_CONSUME_BY_HSR;

    }


}
