package postpacks;


import postdispatcher.Dispatcher;
import solarsystem.CosmicBody;
import solarsystem.SolarSystem;

import java.util.Random;

public class PacksGenerator implements Runnable {

    private static final int MAXIMUM_POSTPACKS_WEIGHT = 80;
    private SolarSystem solarSystem;
    private int packsAmountToGenerate;
    private Dispatcher dispatcher;

    public PacksGenerator(SolarSystem solarSystem, int packsAmountToGenerate) {
        this.solarSystem = solarSystem;
        this.packsAmountToGenerate = packsAmountToGenerate;
        this.dispatcher = solarSystem.getDispatcher();
    }


    private void generateAmountOfPostPacks(int amount) {


        for (int i = 0; i < amount; i++) {
            Random random = new Random();
            double weight = random.nextInt(MAXIMUM_POSTPACKS_WEIGHT) + 1;
            int currentIndex = random.nextInt(solarSystem.getCosmicBodiesWithPostStations().size());
            int destinationIndex = random.nextInt(solarSystem.getCosmicBodiesWithPostStations().size());
            while (destinationIndex == currentIndex) {
                destinationIndex = random.nextInt(solarSystem.getCosmicBodiesWithPostStations().size());
            }
            CosmicBody current = solarSystem.getCosmicBodiesWithPostStations().get(currentIndex);
            CosmicBody destination = solarSystem.getCosmicBodiesWithPostStations().get(destinationIndex);
            PostPack postPack = new PostPack(dispatcher.getPostPacksInJorey().size(), current, destination, weight);
            dispatcher.addNewPack(postPack);
            solarSystem.getCosmicBodiesWithPostStations().
                    get(currentIndex).getPostStation().addPostPackOnStation(postPack);
            System.out.println("CREATED: new " + postPack.toString());
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void run() {
        generateAmountOfPostPacks(packsAmountToGenerate);

    }


}
