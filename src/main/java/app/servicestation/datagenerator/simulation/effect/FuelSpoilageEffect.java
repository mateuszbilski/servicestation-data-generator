package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;

public class FuelSpoilageEffect implements SimulationEffect {

    public static final String TYPE = FuelSpoilageEffect.class.getCanonicalName();

    private Double leakageVolume;
    private Integer duration;
    private Identifier tankIdentifier;

    public FuelSpoilageEffect(Double leakageVolume, Integer duration, Identifier tankIdentifier) {
        this.leakageVolume = leakageVolume;
        this.duration = duration;
        this.tankIdentifier = tankIdentifier;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {
    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        stationManager.spoilFuel(stationManager.getTank(tankIdentifier), leakageVolume);
    }

    @Override
    public void cleanup(ServiceStationManager stationManager) {
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public Integer decrementDuration() {
        return --duration;
    }

    @Override
    public Identifier getIdentifier() {
        return tankIdentifier;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Double getLeakageVolume() {
        return leakageVolume;
    }
}
