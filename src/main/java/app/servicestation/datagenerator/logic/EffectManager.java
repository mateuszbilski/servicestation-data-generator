package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.def.SimulationEffectDef;

import java.util.List;
import java.util.Map;

public interface EffectManager {

    void processEffects(ServiceStationManager serviceStationManager);

}
