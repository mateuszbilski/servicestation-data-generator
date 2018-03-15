package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.FuelSpoilageEffect;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class FuelSpoilageEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private ParameterDef<Boolean> eventProbability;
    private ParameterDef<Double> leakageVolume;
    private ParameterDef<Integer> duration;

    private FuelSpoilageEffectDef(Identifier identifier, ParameterDef<Boolean> eventProbability, ParameterDef<Double> leakageVolume, ParameterDef<Integer> duration) {
        this.identifier = identifier;
        this.eventProbability = eventProbability;
        this.leakageVolume = leakageVolume;
        this.duration = duration;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return eventProbability.generate() &&
                effectList.stream().filter(i -> i.getType().equals(FuelSpoilageEffect.TYPE)).count() == 0;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new FuelSpoilageEffect(leakageVolume.generate(), duration.generate(), identifier);
    }


    public static final class Builder {
        private Identifier identifier;
        private ParameterDef<Boolean> eventProbability;
        private ParameterDef<Double> leakageVolume;
        private ParameterDef<Integer> duration;

        private Builder() {
        }

        public static Builder aFuelSpoilageEffectDef() {
            return new Builder();
        }

        public Builder withIdentifier(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder withEventProbability(ParameterDef<Boolean> eventProbability) {
            this.eventProbability = eventProbability;
            return this;
        }

        public Builder withLeakageVolume(ParameterDef<Double> leakageVolume) {
            this.leakageVolume = leakageVolume;
            return this;
        }

        public Builder withDuration(ParameterDef<Integer> duration) {
            this.duration = duration;
            return this;
        }

        public FuelSpoilageEffectDef build() {
            FuelSpoilageEffectDef fuelSpoilageEffectDef = new FuelSpoilageEffectDef(identifier, eventProbability, leakageVolume, duration);
            return fuelSpoilageEffectDef;
        }
    }
}
