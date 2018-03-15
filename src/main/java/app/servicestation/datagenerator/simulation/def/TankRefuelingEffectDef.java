package app.servicestation.datagenerator.simulation.def;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;
import app.servicestation.datagenerator.simulation.effect.TankRefuelingEffect;
import app.servicestation.datagenerator.simulation.param.ParameterDef;

import java.util.List;

public class TankRefuelingEffectDef implements SimulationEffectDef {

    private Identifier identifier;
    private Double minimalFuelVolume;
    private ParameterDef<Double> refuelVolume;
    private ParameterDef<Integer> duration;

    private TankRefuelingEffectDef(Identifier identifier, Double minimalFuelVolume, ParameterDef<Double> refuelVolume, ParameterDef<Integer> duration) {
        this.identifier = identifier;
        this.minimalFuelVolume = minimalFuelVolume;
        this.refuelVolume = refuelVolume;
        this.duration = duration;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean condition(ServiceStationManager serviceStationManager, List<SimulationEffect> effectList) {
        return effectList.stream().filter(i -> i.getType().equals(TankRefuelingEffect.TYPE)).count() == 0 &&
                serviceStationManager.getTank(identifier).getFuelVolume() < minimalFuelVolume;
    }

    @Override
    public SimulationEffect create(ServiceStationManager serviceStationManager) {
        return new TankRefuelingEffect(identifier, refuelVolume.generate(), duration.generate());
    }


    public static final class Builder {
        private Identifier identifier;
        private Double minimalFuelVolume;
        private ParameterDef<Double> refuelVolume;
        private ParameterDef<Integer> duration;

        private Builder() {
        }

        public static Builder aTankRefuelingEffectDef() {
            return new Builder();
        }

        public Builder withIdentifier(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder withMinimalFuelVolume(Double minimalFuelVolume) {
            this.minimalFuelVolume = minimalFuelVolume;
            return this;
        }

        public Builder withRefuelVolume(ParameterDef<Double> refuelVolume) {
            this.refuelVolume = refuelVolume;
            return this;
        }

        public Builder withDuration(ParameterDef<Integer> duration) {
            this.duration = duration;
            return this;
        }

        public TankRefuelingEffectDef build() {
            TankRefuelingEffectDef tankRefuelingEffectDef = new TankRefuelingEffectDef(identifier, minimalFuelVolume, refuelVolume, duration);
            return tankRefuelingEffectDef;
        }
    }
}
