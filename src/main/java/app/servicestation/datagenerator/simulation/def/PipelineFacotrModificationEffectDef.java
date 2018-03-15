package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.PipelineFactorModificationEffect;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class PipelineFacotrModificationEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private ParameterDef<Boolean> eventProbability;
    private ParameterDef<Double> modificationFactor;

    private PipelineFacotrModificationEffectDef(Identifier identifier, ParameterDef<Boolean> eventProbability, ParameterDef<Double> modificationFactor) {
        this.identifier = identifier;
        this.eventProbability = eventProbability;
        this.modificationFactor = modificationFactor;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return eventProbability.generate() &&
                effectList.stream().filter(i -> i.getType().equals(PipelineFactorModificationEffect.TYPE)).count() == 0;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new PipelineFactorModificationEffect(identifier, modificationFactor.generate());
    }


    public static final class Builder {
        private Identifier identifier;
        private ParameterDef<Boolean> eventProbability;
        private ParameterDef<Double> modificationFactor;

        private Builder() {
        }

        public static Builder aPipelineFacotrModificationEffectDef() {
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

        public Builder withModificationFactor(ParameterDef<Double> modificationFactor) {
            this.modificationFactor = modificationFactor;
            return this;
        }

        public PipelineFacotrModificationEffectDef build() {
            PipelineFacotrModificationEffectDef pipelineFacotrModificationEffectDef = new PipelineFacotrModificationEffectDef(identifier, eventProbability, modificationFactor);
            return pipelineFacotrModificationEffectDef;
        }
    }
}
