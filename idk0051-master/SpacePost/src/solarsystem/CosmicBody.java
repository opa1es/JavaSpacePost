package solarsystem;


import poststations.PostStation;
import poststations.rocketmaintenance.SpecialRocketMaintenance;
import poststations.rocketmaintenance.StandardRocketMaintenance;

public class CosmicBody {


    private PostStation postStation;
    private String name;


    private SolarSystem solarSystem;

    CosmicBody(String name) {
        this.name = name;
        this.postStation = new PostStation();
        this.postStation.setCosmicBody(this);
        if (name.equals("Jupiter") || name.equals("Neptune")) {
            this.postStation.setRocketMaintenance(new SpecialRocketMaintenance());
        } else {
            this.postStation.setRocketMaintenance(new StandardRocketMaintenance());
        }
    }


    public PostStation getPostStation() {
        return postStation;
    }

    public String getName() {
        return name;
    }

    void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    @Override
    public String toString() {
        return name;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }


}
