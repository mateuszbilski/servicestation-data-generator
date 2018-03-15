package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.NozzleFactorModificationEffect;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class NozzleFactorModificationEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private ParameterDef<Boolean> eventProbability;
    private ParameterDef<Double> tempModificationFactor;
    private ParameterDef<Double> inaccuracyModificationFactor;

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return eventProbability.generate() &&
                effectList.stream().filter(i -> i.getType().equals(NozzleFactorModificationEffect.TYPE)).count() == 0;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new NozzleFactorModificationEffect(tempModificationFactor.generate(), identifier,
                inaccuracyModificationFactor.generate());
    }
}
