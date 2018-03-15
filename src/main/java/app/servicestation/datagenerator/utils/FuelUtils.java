package app.servicestation.datagenerator.utils;

import app.servicestation.datagenerator.model.SubstancePortion;

public class FuelUtils {

    public static final Double EXPANSION_FACTOR = 950 * 1e-6;
    public static final Double NET_TEMPERATURE = 15.0;

    FuelUtils() {
    }

    public static Double volumeChangedTemperature(Double volume, Double oldTemperature, Double newTemperature) {
        return (volume * (1 + EXPANSION_FACTOR * (newTemperature - oldTemperature)));
    }

    public static SubstancePortion portionChangedTemperature(SubstancePortion portion, Double newTemperature) {
        return new SubstancePortion(volumeChangedTemperature(portion.getVolume(), portion.getTemperature(), newTemperature),
                newTemperature);
    }

    public static Double netVolume(Double grossVolume, Double temperature) {
        return (grossVolume * (1 + EXPANSION_FACTOR * (NET_TEMPERATURE - temperature)));
    }

    public static SubstancePortion portionNetVolume(SubstancePortion portion) {
        return new SubstancePortion(netVolume(portion.getVolume(), portion.getTemperature()), NET_TEMPERATURE);
    }

}
