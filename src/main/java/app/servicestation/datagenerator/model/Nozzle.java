package app.servicestation.datagenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nozzle implements Measurable {

    public static final Identifier GLOBAL_NET_VOLUME_IDENTIFIER = new Identifier("globalNetVolume");
    public static final Identifier GLOBAL_GROSS_VOLUME_IDENTIFIER = new Identifier("globalGrossVolume");
    public static final Identifier CURRENT_REFUELING_NET_VOLUME_IDENTIFIER = new Identifier("currentRefuelingNetVolume");
    public static final Identifier CURRENT_REFUELING_GROSS_VOLUME_IDENTIFIER = new Identifier("currentRefuelingGrossVolume");
    public static final Identifier LAST_REFUELING_NET_VOLUME_IDENTIFIER = new Identifier("lastRefuelingNetVolume");
    public static final Identifier LAST_REFUELING_GROSS_VOLUME_IDENTIFIER = new Identifier("lastRefuelingGrossVolume");
    public static final Identifier SERVICING_FLAG_IDENTIFIER = new Identifier("servicingFlag");

    private Identifier identifier;

    private Pipeline pipeline;
    private NozzleFactor nozzleFactor;
    private Boolean servicing = false;

    private Double globalNetVolume = 0.0;
    private Double globalGrossVolume = 0.0;
    private Double currentRefuelingNetVolume = 0.0;
    private Double currentRefuelingGrossVolume = 0.0;
    private Double lastRefuelingNetVolume = 0.0;
    private Double lastRefuelingGrossVolume = 0.0;

    private Map<Identifier, Evaluable<Object>> measureTypes;

    public Nozzle() {
        configureMeasures();
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getType() {
        return "NOZZLE";
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Double getGlobalNetVolume() {
        return globalNetVolume;
    }

    public void setGlobalNetVolume(Double globalNetVolume) {
        this.globalNetVolume = globalNetVolume;
    }

    public Double getGlobalGrossVolume() {
        return globalGrossVolume;
    }

    public void setGlobalGrossVolume(Double globalGrossVolume) {
        this.globalGrossVolume = globalGrossVolume;
    }

    public Double getCurrentRefuelingNetVolume() {
        return currentRefuelingNetVolume;
    }

    public void setCurrentRefuelingNetVolume(Double currentRefuelingNetVolume) {
        this.currentRefuelingNetVolume = currentRefuelingNetVolume;
    }

    public Double getCurrentRefuelingGrossVolume() {
        return currentRefuelingGrossVolume;
    }

    public void setCurrentRefuelingGrossVolume(Double currentRefuelingGrossVolume) {
        this.currentRefuelingGrossVolume = currentRefuelingGrossVolume;
    }

    public Double getLastRefuelingNetVolume() {
        return lastRefuelingNetVolume;
    }

    public void setLastRefuelingNetVolume(Double lastRefuelingNetVolume) {
        this.lastRefuelingNetVolume = lastRefuelingNetVolume;
    }

    public Double getLastRefuelingGrossVolume() {
        return lastRefuelingGrossVolume;
    }

    public void setLastRefuelingGrossVolume(Double lastRefuelingGrossVolume) {
        this.lastRefuelingGrossVolume = lastRefuelingGrossVolume;
    }

    public NozzleFactor getNozzleFactor() {
        return nozzleFactor;
    }

    public void setNozzleFactor(NozzleFactor nozzleFactor) {
        this.nozzleFactor = nozzleFactor;
    }

    @Override
    public Object getMeasure(Identifier identifier) {
        return measureTypes.get(identifier).eval();
    }

    @Override
    public List<Identifier> getMeasureTypes() {
        return new ArrayList<>(measureTypes.keySet());
    }

    private void configureMeasures() {
        measureTypes = new HashMap<>();
        measureTypes.put(GLOBAL_NET_VOLUME_IDENTIFIER, () -> globalNetVolume);
        measureTypes.put(GLOBAL_GROSS_VOLUME_IDENTIFIER, () -> globalGrossVolume);
        measureTypes.put(CURRENT_REFUELING_NET_VOLUME_IDENTIFIER, () -> currentRefuelingNetVolume);
        measureTypes.put(CURRENT_REFUELING_GROSS_VOLUME_IDENTIFIER, () -> currentRefuelingGrossVolume);
        measureTypes.put(LAST_REFUELING_NET_VOLUME_IDENTIFIER, () -> lastRefuelingNetVolume);
        measureTypes.put(LAST_REFUELING_GROSS_VOLUME_IDENTIFIER, () -> lastRefuelingGrossVolume);
        measureTypes.put(SERVICING_FLAG_IDENTIFIER, () -> servicing);
    }

    public Boolean getServicing() {
        return servicing;
    }

    public void setServicing(Boolean servicing) {
        this.servicing = servicing;
    }

    public static class NozzleFactor {
        private Double temperatureFactor;
        private Double calibrationFactor;
        private Double inaccuracyMeterFactor;

        public NozzleFactor(Double temperatureFactor, Double calibrationFactor, Double inaccuracyMeterFactor) {
            this.temperatureFactor = temperatureFactor;
            this.calibrationFactor = calibrationFactor;
            this.inaccuracyMeterFactor = inaccuracyMeterFactor;
        }

        public Double getTemperatureFactor() {
            return temperatureFactor;
        }

        public Double getCalibrationFactor() {
            return calibrationFactor;
        }

        public Double getInaccuracyMeterFactor() {
            return inaccuracyMeterFactor;
        }
    }


    public static final class Builder {
        private Identifier identifier;
        private NozzleFactor nozzleFactor;

        private Builder() {
        }

        public static Builder aNozzle() {
            return new Builder();
        }

        public Builder withIdentifier(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder withNozzleFactor(NozzleFactor nozzleFactor) {
            this.nozzleFactor = nozzleFactor;
            return this;
        }

        public Nozzle build() {
            Nozzle nozzle = new Nozzle();
            nozzle.setIdentifier(identifier);
            nozzle.setNozzleFactor(nozzleFactor);
            return nozzle;
        }
    }
}

