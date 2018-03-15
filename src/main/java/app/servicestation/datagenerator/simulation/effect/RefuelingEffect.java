package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;

public class RefuelingEffect implements SimulationEffect {

    public static final String TYPE = RefuelingEffect.class.getCanonicalName();

    private Integer duration;
    private Identifier nozzleIdentifier;

    public RefuelingEffect(Integer duration, Identifier nozzleIdentifier) {
        this.duration = duration;
        this.nozzleIdentifier = nozzleIdentifier;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {
        stationManager.startRefueling(stationManager.getNozzle(nozzleIdentifier));
    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        stationManager.deliverFuel(stationManager.getNozzle(nozzleIdentifier));
    }

    @Override
    public void cleanup(ServiceStationManager stationManager) {
        stationManager.stopRefueling(stationManager.getNozzle(nozzleIdentifier));
    }

    @Override
    public Identifier getIdentifier() {
        return nozzleIdentifier;
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
}
