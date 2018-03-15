package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Nozzle;

public class NozzleFactorModificationEffect implements SimulationEffect {

    public static final String TYPE = NozzleFactorModificationEffect.class.getCanonicalName();

    private Identifier nozzleIdentifier;
    private Double tempModificationFactor;
    private Double inaccuracyModificationFactor;
    private Integer duration = 1;

    public NozzleFactorModificationEffect(Double tempModificationFactor, Identifier nozzleIdentifier, Double inaccuracyModificationFactor) {
        this.tempModificationFactor = tempModificationFactor;
        this.nozzleIdentifier = nozzleIdentifier;
        this.inaccuracyModificationFactor = inaccuracyModificationFactor;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {

    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        Nozzle nozzle = stationManager.getNozzle(nozzleIdentifier);
        stationManager.changeNozzleFactor(nozzle,
                new Nozzle.NozzleFactor(nozzle.getNozzleFactor().getTemperatureFactor() * tempModificationFactor,
                        nozzle.getNozzleFactor().getCalibrationFactor(),
                        nozzle.getNozzleFactor().getInaccuracyMeterFactor() * inaccuracyModificationFactor));
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

    public Double getTempModificationFactor() {
        return tempModificationFactor;
    }

    public Double getInaccuracyModificationFactor() {
        return inaccuracyModificationFactor;
    }
}
