package rockets;


import postpacks.PostPack;
import rockets.rocketcontroller.RocketFlightController;
import solarsystem.CosmicBody;

import java.util.ArrayList;
import java.util.Arrays;

public class StandardRocket implements Runnable {

    public final static double FULL_TANK = 100;
    public final static double MAX_RADIATION_SENSOR_VALUE = 25;
    private final static double NORMAL_FUEL_CONSUMPTION_PER_FLIGHT = 20;
    private final static double MAX_WEIGHT_STANDARD_ROCKET = 100;
    private final static double FUEL_CONSUMPTION_FROM_HOT_SPOT = 50;
    final static double MAX_WEIGHT_HOTSPOT_ROCKET = 80;

    final ArrayList<String> NOT_ALLOWED_PLANETS_STANDARD_ROCKET = new ArrayList<>(Arrays.asList("Mercury", "Venus"));
    final ArrayList<String> NOT_ALLOWED_PLANETS_HOTSPOT_ROCKET = new ArrayList<>();


    double fuel;
    private final ArrayList<PostPack> postPacksAboard;
    private String id;
    private double radiationSensorValue;
    public CosmicBody currentCosmicBody;
    private RocketFlightController rocketController;

    public StandardRocket(String name, CosmicBody current) {
        this.id = name;
        this.fuel = FULL_TANK;
        this.currentCosmicBody = current;
        this.postPacksAboard = new ArrayList<>();
        this.radiationSensorValue = MAX_RADIATION_SENSOR_VALUE;
        this.rocketController = new RocketFlightController();

    }


    public boolean checkIfNeedFuel() {
        if (NOT_ALLOWED_PLANETS_STANDARD_ROCKET.contains(currentCosmicBody.getName())) {
            return fuel < FUEL_CONSUMPTION_FROM_HOT_SPOT;
        }
        return fuel <= 0;
    }

    public boolean checkIfNeedRadiationSensor() {
        return this.radiationSensorValue <= 2;
    }

    public void consumeFuel() {
        fuel -= NORMAL_FUEL_CONSUMPTION_PER_FLIGHT;
    }

    public void setRadiationSensorValue(double radiationSensorValue) {
        this.radiationSensorValue = radiationSensorValue;
    }

    public void consumeRadiationSensor() {
        radiationSensorValue--;
    }


    private void actInSpace() {
        rocketController.goByFirstActionStep(this);
        rocketController.goBySecondActionStep(this);
    }


    @Override
    public void run() {
        actInSpace();
    }


    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getFuel() {
        return fuel;
    }

    public RocketFlightController getRocketController() {
        return rocketController;
    }

    public double getMaximumPacksWeight() {
        return MAX_WEIGHT_STANDARD_ROCKET;
    }

    public ArrayList<String> getNotAllowedPlanetsNames() {
        return NOT_ALLOWED_PLANETS_STANDARD_ROCKET;
    }

    public ArrayList<PostPack> getPostPacksAboard() {
        return postPacksAboard;
    }


    public void removePostPackFromBord(PostPack postPack) {
        synchronized (postPacksAboard) {

            postPacksAboard.remove(postPack);

        }
    }

    public void addPostPackAboard(PostPack postPack) {
        synchronized (postPacksAboard) {

            postPacksAboard.add(postPack);

        }
    }


    public double getTotalWeight() {
        final double[] totalWeight = {0};
        getPostPacksAboard()
                .forEach(x -> totalWeight[0] += x.getWeight());
        return totalWeight[0];
    }

    public double getRadiationSensorValue() {
        return radiationSensorValue;
    }

    public CosmicBody getCurrentCosmicBody() {
        return currentCosmicBody;
    }

    public String getId() {
        return id;
    }


}
