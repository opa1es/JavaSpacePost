package rockets.rocketcontroller;


import postdispatcher.Dispatcher;
import rockets.StandardRocket;
import solarsystem.CosmicBody;

import java.util.ArrayList;

public class RocketFlightController {


    public CosmicBody getNextDirection(StandardRocket rocket) {
        if (rocket.checkIfNeedRadiationSensor()) {
            for (CosmicBody cosmicBody : getPlanetsToVisit(rocket)) {
                if (cosmicBody.getName().equals("Neptune") || cosmicBody.getName().equals("Jupiter")) {
                    return cosmicBody;
                }
            }

            return rocket.getCurrentCosmicBody().getSolarSystem().getCosmicBodyByName("Jupiter");

        }
        return getPlanetsToVisit(rocket).get(0);
    }


    public ArrayList<CosmicBody> getPlanetsToVisit(StandardRocket rocket) {
        ArrayList<CosmicBody> planets = new ArrayList<>();
        rocket.getPostPacksAboard().stream()
                .filter(x -> !planets.contains(x.getEndPoint()))
                .forEach(x -> planets.add(x.getEndPoint()));
        return planets;
    }


    private void makeFlight(StandardRocket rocket) {
        try {

            rocket.consumeFuel();
            rocket.consumeRadiationSensor();


            System.out.println(rocket.getId() + " FLIGHTS TO " +
                    getNextDirection(rocket).getName() + "| FUEL NOW: " + rocket.getFuel() +
                    "|RSV NOW: " + rocket.getRadiationSensorValue());


            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void goByFirstActionStep(StandardRocket rocket) {
        rocket.getCurrentCosmicBody().getPostStation().uploadRocket(rocket);

        while (!getPlanetsToVisit(rocket).isEmpty()) {
            rocket.currentCosmicBody.getPostStation().getRocketMaintenance().fillRocketResources(rocket);

            makeFlight(rocket);
            rocket.currentCosmicBody = getNextDirection(rocket);


            System.out.println(rocket.getId() + " now on " + rocket.getCurrentCosmicBody().getName());


            rocket.currentCosmicBody.getPostStation().unloadRocket(rocket);

            rocket.currentCosmicBody.getPostStation().uploadRocket(rocket);

        }

    }


    public void goBySecondActionStep(StandardRocket rocket) {

        Dispatcher dispatcher = rocket.getCurrentCosmicBody().getSolarSystem().getDispatcher();

        rocket.currentCosmicBody.getPostStation().getRocketMaintenance().fillRocketResources(rocket);
        while (!dispatcher.checkIfAllPacksAreDelivered()) {


            rocket.currentCosmicBody = dispatcher.getCosmicBodiesWithPostPacks().get(0);
            rocket.currentCosmicBody.getPostStation().uploadRocket(rocket);
            System.out.println(rocket.getId() + " now on " + rocket.getCurrentCosmicBody().getName());
            rocket.currentCosmicBody.getPostStation().getRocketMaintenance().fillRocketResources(rocket);
            makeFlight(rocket);
            rocket.currentCosmicBody = getNextDirection(rocket);

            System.out.println(rocket.getId() + " now on " + rocket.getCurrentCosmicBody().getName());

            rocket.currentCosmicBody.getPostStation().unloadRocket(rocket);
            rocket.currentCosmicBody.getPostStation().getRocketMaintenance().fillRocketResources(rocket);

        }
    }


}
