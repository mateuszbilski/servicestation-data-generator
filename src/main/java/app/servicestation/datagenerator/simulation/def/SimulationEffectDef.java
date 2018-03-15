package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;

import java.util.List;

public interface SimulationEffectDef {

    Identifier getIdentifier();

    boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList);
    SimulationEffect create(ServiceStationManager serviceStationManager);
}
