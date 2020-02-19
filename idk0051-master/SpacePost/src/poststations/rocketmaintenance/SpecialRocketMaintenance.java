package poststations.rocketmaintenance;


import rockets.StandardRocket;

public class SpecialRocketMaintenance extends StandardRocketMaintenance {

    @Override
    public void fillRocketResources(StandardRocket rocket) {

        if (rocket.checkIfNeedRadiationSensor()) {

            rocket.setRadiationSensorValue(StandardRocket.MAX_RADIATION_SENSOR_VALUE);
            System.out.println("Rocket " + rocket.getId() + " RSV is filled!");

        }

        if (rocket.checkIfNeedFuel()) {
            rocket.setFuel(StandardRocket.FULL_TANK);
            System.out.println("Rocket " + rocket.getId() + " tank is filled!");
        }


    }


}
