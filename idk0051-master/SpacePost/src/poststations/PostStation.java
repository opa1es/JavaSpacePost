package poststations;


import postpacks.PostPack;
import poststations.rocketmaintenance.StandardRocketMaintenance;
import rockets.StandardRocket;
import solarsystem.CosmicBody;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PostStation {

    private final ArrayList<PostPack> packsOnStation;
    private final ArrayList<PostPack> arrivedPacks;
    private CosmicBody cosmicBody;
    private double totalWeight = 0;
    private int amountOf = 0;
    private StandardRocketMaintenance rocketMaintenance;

    public PostStation() {
        this.packsOnStation = new ArrayList<>();
        this.arrivedPacks = new ArrayList<>();

    }


    public void addPostPackOnStation(PostPack postPack) {
        synchronized (packsOnStation) {
            packsOnStation.add(postPack);

        }
    }

    public void removePostPack(PostPack postPack) {
        synchronized (packsOnStation) {
            packsOnStation.remove(postPack);
        }
    }

    public void addArrivedPack(PostPack postPack) {
        synchronized (arrivedPacks) {
            arrivedPacks.add(postPack);

            System.out.println("on " + this.toString() + " ARRIVED NEW " + postPack.toString());

            //remove from database
            this.cosmicBody.getSolarSystem().getDispatcher().removePack(postPack);

        }
    }


    public double getTotalPacksWeight() {
        totalWeight = 0;
        arrivedPacks
                .forEach(postPack -> totalWeight += postPack.getWeight());
        return totalWeight;
    }

    public double getAveragePacksWeight() {
        return getTotalPacksWeight() / arrivedPacks.size();


    }

    public int getArrivedPacksAmount() {
        return arrivedPacks.size();
    }

    public ArrayList<String> getPlacesFromPacksArrived() {
        ArrayList<String> places = new ArrayList<>();
        for (PostPack arrivedPack : arrivedPacks) {
            if (!places.contains(arrivedPack.getStartPoint().getName())) {
                places.add(arrivedPack.getStartPoint().getName());
            }
        }
        return places;

    }

    public int getAmountOfArrivedPacksWithParameter(Predicate<PostPack> predicate) {
        amountOf = 0;
        arrivedPacks.stream()
                .filter(predicate)
                .forEach(pack -> amountOf++);
        return amountOf;
    }


    public void uploadRocket(StandardRocket rocket) {
        synchronized (packsOnStation) {

            advancedUpload(rocket);

            for (int i = 0; i < packsOnStation.size(); i++) {
                if (packsOnStation.get(i).getWeight() + rocket.getTotalWeight() <= rocket.getMaximumPacksWeight() &&
                        !rocket.getNotAllowedPlanetsNames().
                                contains(this.packsOnStation.get(i).getEndPoint().getName())) {

                    rocket.addPostPackAboard(packsOnStation.get(i));
                    System.out.println("UPLOADED" + this.packsOnStation.get(i).toString());
                    removePostPack(packsOnStation.get(i));
                }
            }
        }
    }

    public void unloadRocket(StandardRocket rocket) {
        synchronized (packsOnStation) {
            System.out.println(":::UNLOADING PROCESS");
            for (int i = 0; i < rocket.getPostPacksAboard().size(); i++) {
                if (rocket.getPostPacksAboard().get(i).getEndPoint().getName().equals(this.getCosmicBody().getName())) {
                    System.out.println("DROPPED (" + rocket.getId() + ")" +
                            rocket.getPostPacksAboard().get(i).toString());

                    this.addArrivedPack(rocket.getPostPacksAboard().get(i));
                    rocket.removePostPackFromBord(rocket.getPostPacksAboard().get(i));
                }
            }
        }
    }

    private void advancedUpload(StandardRocket rocket) {
        ArrayList<CosmicBody> destinationsToUploadFirst = rocket.getRocketController().getPlanetsToVisit(rocket);

        if (!destinationsToUploadFirst.isEmpty()) {
            for (int i = 0; i < packsOnStation.size(); i++) {
                if (packsOnStation.get(i).getWeight() + rocket.getTotalWeight() <= rocket.getMaximumPacksWeight() &&
                        !rocket.getNotAllowedPlanetsNames().
                                contains(this.packsOnStation.get(i).getEndPoint().getName())
                        && destinationsToUploadFirst.contains(packsOnStation.get(i).getEndPoint())) {

                    rocket.addPostPackAboard(packsOnStation.get(i));
                    System.out.println("UPLOADED(PREFERENCE): " + this.packsOnStation.get(i).toString());
                    removePostPack(packsOnStation.get(i));
                }
            }
        }

    }


    public void setCosmicBody(CosmicBody cosmicBody) {
        this.cosmicBody = cosmicBody;
    }

    public ArrayList<PostPack> getPacksOnStation() {
        return packsOnStation;
    }

    public ArrayList<PostPack> getArrivedPacks() {
        return arrivedPacks;
    }

    public int getAmountOf() {
        return amountOf;
    }

    public StandardRocketMaintenance getRocketMaintenance() {
        return rocketMaintenance;
    }


    public CosmicBody getCosmicBody() {
        return cosmicBody;
    }

    public void setRocketMaintenance(StandardRocketMaintenance rocketMaintenance) {
        this.rocketMaintenance = rocketMaintenance;
    }


    @Override
    public String toString() {
        return "POST STATION: " + this.cosmicBody.getName().toUpperCase();
    }

}
