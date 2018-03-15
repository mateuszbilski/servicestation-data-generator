package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Pipeline;

public class PipelineFactorModificationEffect implements SimulationEffect {

    public static final String TYPE = PipelineFactorModificationEffect.class.getCanonicalName();

    private Identifier nozzleIdentifier;
    private Double modificationFactor;
    private Integer duration = 1;

    public PipelineFactorModificationEffect(Identifier nozzleIdentifier, Double modificationFactor) {
        this.nozzleIdentifier = nozzleIdentifier;
        this.modificationFactor = modificationFactor;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {

    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        Pipeline pipeline = stationManager.getNozzle(nozzleIdentifier).getPipeline();
        stationManager.changePipelineFactor(
                pipeline,
                new Pipeline.PipelineFactor(pipeline.getPipelineFactor().getTemperatureFactor() * modificationFactor)
        );
    }

    @Override
    public void cleanup(ServiceStationManager stationManager) {

    }

    @Override
    public Identifier getIdentifier() {
        return nozzleIdentifier;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public Integer decrementDuration() {
        return --duration;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Double getModificationFactor() {
        return modificationFactor;
    }
}
