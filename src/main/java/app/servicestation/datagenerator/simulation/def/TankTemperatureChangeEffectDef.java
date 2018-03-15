package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.effect.TankTemperatureChangeEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class TankTemperatureChangeEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private ParameterDef<Boolean> eventProbability;
    private ParameterDef<Double> temperatureChangeValue;
    private ParameterDef<Integer> duration;

    private TankTemperatureChangeEffectDef(Identifier identifier, ParameterDef<Boolean> eventProbability, ParameterDef<Double> temperatureChangeValue, ParameterDef<Integer> duration) {
        this.identifier = identifier;
        this.eventProbability = eventProbability;
        this.temperatureChangeValue = temperatureChangeValue;
        this.duration = duration;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return eventProbability.generate() &&
                effectList.stream().filter(i -> i.getType().equals(TankTemperatureChangeEffect.TYPE)).count() == 0;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new TankTemperatureChangeEffect(identifier, temperatureChangeValue.generate(), duration.generate());
    }


    public static final class Builder {
        private Identifier identifier;
        private ParameterDef<Boolean> eventProbability;
        private ParameterDef<Double> temperatureChangeValue;
        private ParameterDef<Integer> duration;

        private Builder() {
        }

        public static Builder aTankTemperatureChangeEffectDef() {
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

        public Builder withTemperatureChangeValue(ParameterDef<Double> temperatureChangeValue) {
            this.temperatureChangeValue = temperatureChangeValue;
            return this;
        }

        public Builder withDuration(ParameterDef<Integer> duration) {
            this.duration = duration;
            return this;
        }

        public TankTemperatureChangeEffectDef build() {
            TankTemperatureChangeEffectDef tankTemperatureChangeEffectDef = new TankTemperatureChangeEffectDef(identifier, eventProbability, temperatureChangeValue, duration);
            return tankTemperatureChangeEffectDef;
        }
    }
}
