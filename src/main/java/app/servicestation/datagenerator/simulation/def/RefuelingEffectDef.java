package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.RefuelingEffect;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class RefuelingEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private ParameterDef<Integer> duration;
    private ParameterDef<Boolean> eventProbability;

    private RefuelingEffectDef(Identifier identifier, ParameterDef<Integer> duration, ParameterDef<Boolean> eventProbability) {
        this.identifier = identifier;
        this.duration = duration;
        this.eventProbability = eventProbability;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return eventProbability.generate() &&
                effectList.stream().filter(i -> i.getType().equals(RefuelingEffect.TYPE)).count() == 0;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new RefuelingEffect(duration.generate(), identifier);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public ParameterDef<Integer> getDuration() {
        return duration;
    }


    public static final class Builder {
        private Identifier identifier;
        private ParameterDef<Integer> duration;
        private ParameterDef<Boolean> eventProbability;

        private Builder() {
        }

        public static Builder aRefuelingEffectDef() {
            return new Builder();
        }

        public Builder withIdentifier(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder withDuration(ParameterDef<Integer> duration) {
            this.duration = duration;
            return this;
        }

        public Builder withEventProbability(ParameterDef<Boolean> eventProbability) {
            this.eventProbability = eventProbability;
            return this;
        }

        public RefuelingEffectDef build() {
            RefuelingEffectDef refuelingEffectDef = new RefuelingEffectDef(identifier, duration, eventProbability);
            return refuelingEffectDef;
        }
    }
}
