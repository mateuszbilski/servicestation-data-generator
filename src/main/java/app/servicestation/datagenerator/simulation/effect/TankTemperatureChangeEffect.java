package app.servicestation.datagenerator.simulation.effect;

import app.servicestation.datagenerator.logic.ServiceStationManager;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Tank;

public class TankTemperatureChangeEffect implements SimulationEffect {

    public static final String TYPE = TankTemperatureChangeEffect.class.getCanonicalName();

    private Identifier tankIdentifier;
    private Double temperatureChangeValue;
    private Integer duration;

    public TankTemperatureChangeEffect(Identifier tankIdentifier, Double temperatureChangeValue, Integer duration) {
        this.tankIdentifier = tankIdentifier;
        this.temperatureChangeValue = temperatureChangeValue;
        this.duration = duration;
    }

    @Override
    public Identifier getIdentifier() {
        return tankIdentifier;
    }

    @Override
    public void prepare(ServiceStationManager stationManager) {

    }

    @Override
    public void apply(ServiceStationManager stationManager) {
        Tank tank = stationManager.getTank(tankIdentifier);
        stationManager.changeTankTemperature(tank, temperatureChangeValue);
    }

    @Override
    public void cleanup(ServiceStationManager stationManager) {

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

    public Double getTemperatureChangeValue() {
        return temperatureChangeValue;
    }
}
