package app.servicestation.datagenerator.model;

public class SubstancePortion {

    private Double volume;
    private Double temperature;

    public SubstancePortion(Double volume, Double temperature) {
        this.volume = volume;
        this.temperature = temperature;
    }

    public Double getVolume() {
        return volume;
    }

    public Double getTemperature() {
        return temperature;
    }

}