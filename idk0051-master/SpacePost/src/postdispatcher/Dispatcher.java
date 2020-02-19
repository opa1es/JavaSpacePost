package postdispatcher;


import postpacks.PostPack;
import solarsystem.CosmicBody;

import java.util.ArrayList;

public class Dispatcher {

    private final ArrayList<PostPack> postPacksInJorey;

    public Dispatcher() {

        postPacksInJorey = new ArrayList<>();
    }


    public void addNewPack(PostPack postPack) {
        synchronized (postPacksInJorey) {
            postPacksInJorey.add(postPack);
        }
    }

    public void removePack(PostPack postPack) {
        synchronized (postPacksInJorey) {
            postPacksInJorey.remove(postPack);
            System.out.println("                                                                         DATABASE SIZE:"
                    + this.getPostPacksInJorey().size());

        }
    }

    public ArrayList<CosmicBody> getCosmicBodiesWithPostPacks() {
        synchronized (postPacksInJorey) {

            ArrayList<CosmicBody> cosmicBodies = new ArrayList<>();

            postPacksInJorey.stream()
                    .filter(x -> !cosmicBodies.contains(x.getStartPoint()))
                    .forEach(x -> cosmicBodies.add(x.getStartPoint()));

            return cosmicBodies;
        }
    }

    public boolean checkIfAllPacksAreDelivered() {
        synchronized (postPacksInJorey) {
            return postPacksInJorey.isEmpty();
        }
    }

    public ArrayList<PostPack> getPostPacksInJorey() {
        return postPacksInJorey;
    }
}
