package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;

public interface SimulationEffect {

    Identifier getIdentifier();

    void prepare(ServiceStationManager stationManager);
    void apply(ServiceStationManager stationManager);
    void cleanup(ServiceStationManager stationManager);

    Integer getDuration();
    Integer decrementDuration();
    String getType();
}
