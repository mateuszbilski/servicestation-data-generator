package app.servicestation.datagenerator.model;

import app.servicestation.datagenerator.utils.FuelUtils;
import app.servicestation.datagenerator.utils.WaterUtils;

import java.util.*;

public class Tank implements Measurable {

    public static final Identifier TEMPERATURE_MEASURE_IDENTIFIER = new Identifier("temperature");
    public static final Identifier WATER_NET_VOLUME_MEASURE_IDENTIFIER = new Identifier("waterNettoVolume");
    public static final Identifier WATER_GROSS_VOLUME_MEASURE_IDENTIFIER = new Identifier("waterGrossVolume");
    public static final Identifier FUEL_NET_VOLUME_MEASURE_IDENTIFIER = new Identifier("fuelNettoVolume");
    public static final Identifier FUEL_GROSS_VOLUME_MEASURE_IDENTIFIER = new Identifier("fuelGrossVolume");

    private Identifier identifier;
    private Double temperature;
    private Double fuelVolume;
    private Double waterVolume;

    private Map<Identifier, Evaluable<Object>> measureTypes;

    public Tank() {
        configureMeasures();
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getType() {
        return "TANK";
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Double getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(Double waterVolume) {
        this.waterVolume = waterVolume;
    }

    public Object getMeasure(Identifier identifier) {
        return measureTypes.get(identifier).eval();
    }

    public List<Identifier> getMeasureTypes() {
        return new ArrayList<>(measureTypes.keySet());
    }

    private void configureMeasures() {
        measureTypes = new HashMap<>();
        measureTypes.put(TEMPERATURE_MEASURE_IDENTIFIER, () -> temperature);
        measureTypes.put(WATER_NET_VOLUME_MEASURE_IDENTIFIER, () -> WaterUtils.netVolume(waterVolume, temperature));
        measureTypes.put(WATER_GROSS_VOLUME_MEASURE_IDENTIFIER, () -> waterVolume);
        measureTypes.put(FUEL_NET_VOLUME_MEASURE_IDENTIFIER, () -> FuelUtils.netVolume(fuelVolume, temperature));
        measureTypes.put(FUEL_GROSS_VOLUME_MEASURE_IDENTIFIER, () -> fuelVolume);
    }


    public static final class Builder {
        private Identifier identifier;
        private Double temperature;
        private Double fuelVolume;
        private Double waterVolume;

        private Builder() {
        }

        public static Builder aTank() {
            return new Builder();
        }

        public Builder withIdentifier(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder withTemperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withFuelVolume(Double fuelVolume) {
            this.fuelVolume = fuelVolume;
            return this;
        }

        public Builder withWaterVolume(Double waterVolume) {
            this.waterVolume = waterVolume;
            return this;
        }

        public Tank build() {
            Tank tank = new Tank();
            tank.setIdentifier(identifier);
            tank.setTemperature(temperature);
            tank.setFuelVolume(fuelVolume);
            tank.setWaterVolume(waterVolume);
            return tank;
        }
    }
}
