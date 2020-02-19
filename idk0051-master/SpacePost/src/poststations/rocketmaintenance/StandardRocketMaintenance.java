package poststations.rocketmaintenance;


import rockets.StandardRocket;

public class StandardRocketMaintenance {

    public void fillRocketResources(StandardRocket rocket) {

        if (rocket.checkIfNeedFuel()) {

            rocket.setFuel(StandardRocket.FULL_TANK);
            System.out.println("Rocket " + rocket.getId() + " tank is filled!");

        }
    }


}
