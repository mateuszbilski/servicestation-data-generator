package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.simulation.def.SimulationEffectDef;
import app.servicestation.datagenerator.simulation.effect.SimulationEffect;

import java.util.*;
import java.util.stream.Collectors;

public class EffectManagerImpl implements EffectManager {

    private Map<Identifier, List<SimulationEffectDef>> effectDefList = new HashMap<>();
    private Map<Identifier, List<SimulationEffect>> runningEffectList = new HashMap<>();

    private EffectManagerImpl() {

    }

    @Override
    public void processEffects(ServiceStationManager serviceStationManager) {
        //Create new effects
        effectDefList.forEach((id, list) ->
                list.stream()
                        .filter(def -> def.condition(serviceStationManager, runningEffectList.get(id)))
                        .forEach(def -> {
                            SimulationEffect effect = def.create(serviceStationManager);
                            runningEffectList.get(id).add(effect);
                            effect.prepare(serviceStationManager);
                        })
        );

        //Apply effects
        runningEffectList.forEach((id, list) ->
                list.forEach(item -> {
                    item.apply(serviceStationManager);
                    item.decrementDuration();
                })
        );

        //Cleanup effects
        runningEffectList.forEach((id, list) -> {
            list.stream().filter(item -> item.getDuration() == 0).forEach(item -> item.cleanup(serviceStationManager));
            list.removeIf(item -> item.getDuration() == 0);
        });
    }

    private void loadEffectDefList(Map<Identifier, List<SimulationEffectDef>> effectDefList) {
        this.effectDefList = effectDefList;
        runningEffectList = effectDefList.keySet().stream().collect(Collectors.toMap(key -> key, val -> new ArrayList<>()));
    }

    public static final class Builder {
        Map<Identifier, List<SimulationEffectDef>> effectDefList = new HashMap<>();

        public static EffectManagerImpl.Builder aEffectManagerImpl() {
            return new EffectManagerImpl.Builder();
        }

        private Builder() {

        }

        public Builder addEffectDef(SimulationEffectDef effectDef) {
            if (!effectDefList.containsKey(effectDef.getIdentifier())) {
                effectDefList.put(effectDef.getIdentifier(), new ArrayList<>());
            }
            effectDefList.get(effectDef.getIdentifier()).add(effectDef);
            return this;
        }

        public EffectManagerImpl build() {
            EffectManagerImpl obj = new EffectManagerImpl();
            obj.loadEffectDefList(effectDefList);
            return obj;
        }
    }

}
