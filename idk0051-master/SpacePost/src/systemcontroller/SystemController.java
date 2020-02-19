package systemcontroller;


import postpacks.PacksGenerator;
import rockets.HotSpotRocket;
import rockets.StandardRocket;
import solarsystem.SolarSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemController {


    public static void main(String[] args) {

        SolarSystem solarSystem = new SolarSystem();
        ExecutorService systemEX = Executors.newFixedThreadPool(26);

        PacksGenerator packsGenerator = new PacksGenerator(solarSystem, 500);
        packsGenerator.run();


        for (int i = 0; i < 0; i++) {

            StandardRocket standardRocket1 = new StandardRocket("RR-" + i + i + i + i, solarSystem.getCosmicBodiesWithPostStations().get(2));
            systemEX.submit(standardRocket1);

        }
        for (int i = 0; i < 1; i++) {

            HotSpotRocket standardRocket1 = new HotSpotRocket("HSR-" + i + i + i + i, solarSystem.getCosmicBodiesWithPostStations().get(2));
            systemEX.submit(standardRocket1);

        }


        systemEX.shutdown();


        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("TOTALLY ARRIVED: " + solarSystem.getCosmicBodyByName("Earth").getPostStation().getArrivedPacksAmount());
        System.out.println("FROM PLACES: " + solarSystem.getCosmicBodyByName("Earth").getPostStation().getPlacesFromPacksArrived());
        System.out.println("TOTAL WEIGHT: " + solarSystem.getCosmicBodyByName("Earth").getPostStation().getTotalPacksWeight());
        System.out.println("AVERAGE WEIGHT: " + solarSystem.getCosmicBodyByName("Earth").getPostStation().getAveragePacksWeight());
        System.out.println("NUMBER WITH PARAMETER: " + solarSystem.getCosmicBodyByName(
                "Earth").getPostStation().getAmountOfArrivedPacksWithParameter(x -> x.getWeight() < 30));


    }

}

