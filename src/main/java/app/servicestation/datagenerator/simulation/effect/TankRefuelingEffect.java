package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;

public class TankRefuelingEffect implements SimulationEffect {

    public static final String TYPE = TankRefuelingEffect.class.getCanonicalName();

    private Identifier tankIdentifier;
    private Double refuelVolume;
    private Integer duration;

    public TankRefuelingEffect(Identifier tankIdentifier, Double refuelVolume, Integer duration) {
        this.tankIdentifier = tankIdentifier;
        this.refuelVolume = refuelVolume;
        this.duration = duration;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {
    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        stationManager.refuelTank(stationManager.getTank(tankIdentifier), refuelVolume);
    }

    @Override
    public void cleanup(ServiceStationManager stationManager) {
    }

    @Override
    public Identifier getIdentifier() {
        return tankIdentifier;
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
    public String getType() {
        return TYPE;
    }

    public Double getRefuelVolume() {
        return refuelVolume;
    }
}
