package postpacks;


import solarsystem.CosmicBody;

public class PostPack {


    private double weight;
    private long id;
    private CosmicBody startPoint;
    private CosmicBody endPoint;


    public PostPack(long id, CosmicBody startPoint, CosmicBody endPoint, double weight) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.weight = weight;
    }


    public double getWeight() {
        return weight;
    }

    public long getId() {
        return id;
    }

    public CosmicBody getStartPoint() {
        return startPoint;
    }

    public CosmicBody getEndPoint() {
        return endPoint;
    }

    @Override
    public String toString() {
        return "PACK: №" + id + "| " + startPoint.getName() + " - " + endPoint.getName() + "(" + weight + "kg)";
    }

}
