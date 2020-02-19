package solarsystem;


import postdispatcher.Dispatcher;

import java.util.ArrayList;
import java.util.Arrays;

public class SolarSystem {

    private ArrayList<CosmicBody> cosmicBodiesWithPostStations = new ArrayList<>();

    private Dispatcher dispatcher;

    public SolarSystem() {
        this.dispatcher = new Dispatcher();
        setUpCosmicBodies();
    }

    private void setUpCosmicBodies() {
        ArrayList<String> planetNames = new ArrayList<>(Arrays.asList("Mercury", "Venus", "Earth", "Pluto", "Io",
                "Moon", "Neptune", "Mars", "Jupiter", "Saturn", "Uranus"));
        planetNames
                .forEach(cosmicBody -> {
                    CosmicBody cosmicBody1 = new CosmicBody(cosmicBody);
                    cosmicBody1.setSolarSystem(this);
                    cosmicBodiesWithPostStations.add(cosmicBody1);
                });


    }

    public ArrayList<CosmicBody> getCosmicBodiesWithPostStations() {
        return cosmicBodiesWithPostStations;
    }


    public CosmicBody getCosmicBodyByName(String name) {
        final CosmicBody[] cosmicBodyToReturn = new CosmicBody[1];
        cosmicBodiesWithPostStations.stream()
                .filter(cosmicBody -> cosmicBody.getName().equals(name))
                .findFirst()
                .ifPresent(x -> cosmicBodyToReturn[0] = x);


        return cosmicBodyToReturn[0];
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }
}
