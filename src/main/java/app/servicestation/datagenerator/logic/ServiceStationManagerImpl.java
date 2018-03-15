package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.model.*;
import app.servicestation.datagenerator.utils.FuelUtils;

import java.util.*;

public class ServiceStationManagerImpl implements ServiceStationManager {

    private static final double MIN_TEMP_BOUND = 1.0;
    private static final double MAX_TEMP_BOUND = 60.0;
    private Map<Identifier, Tank> tanks = new HashMap<>();
    private Map<Identifier, Nozzle> nozzles = new HashMap<>();
    private List<Measurable> measurableList = new ArrayList<>();

    private ServiceStationManagerImpl() {

    }

    @Override
    public Tank getTank(Identifier identifier) {
        return tanks.get(identifier);
    }

    @Override
    public Nozzle getNozzle(Identifier identifier) {
        return nozzles.get(identifier);
    }

    @Override
    public List<Measurable> getMeasurableList() {
        return measurableList;
    }

    @Override
    public void startRefueling(Nozzle nozzle) {
        if (nozzle.getServicing()) {
            throw new IllegalStateException();
        }
        nozzle.setServicing(true);
    }

    @Override
    public void stopRefueling(Nozzle nozzle) {
        if (!nozzle.getServicing()){
            throw new IllegalStateException();
        }
        nozzle.setServicing(false);
        nozzle.setLastRefuelingGrossVolume(nozzle.getCurrentRefuelingGrossVolume());
        nozzle.setLastRefuelingNetVolume(nozzle.getCurrentRefuelingNetVolume());
        nozzle.setCurrentRefuelingGrossVolume(0.0);
        nozzle.setCurrentRefuelingNetVolume(0.0);
    }

    @Override
    public void deliverFuel(Nozzle nozzle) {
        Pipeline pipeline = nozzle.getPipeline();
        Tank tank = nozzle.getPipeline().getTank();
        SubstancePortion fuelPortion = new SubstancePortion(
                (pipeline.getThroughputCapacity() < tank.getFuelVolume() ? pipeline.getThroughputCapacity() : tank.getFuelVolume()),
                tank.getTemperature()
        );
        tank.setFuelVolume(tank.getFuelVolume() - fuelPortion.getVolume());

        //Delivering fuel through pipe
        fuelPortion = FuelUtils.portionChangedTemperature(fuelPortion,
                pipeline.getPipelineFactor().getTemperatureFactor() * fuelPortion.getTemperature());

        //Delivering fuel through nozzle
        fuelPortion = FuelUtils.portionChangedTemperature(fuelPortion,
                nozzle.getNozzleFactor().getTemperatureFactor() * fuelPortion.getTemperature());

        Double nozzleInaccuracyFactor = nozzle.getNozzleFactor().getCalibrationFactor() *
                nozzle.getNozzleFactor().getInaccuracyMeterFactor();
        Double grossVolume = fuelPortion.getVolume() * nozzleInaccuracyFactor;
        Double netVolume = FuelUtils.portionNetVolume(fuelPortion).getVolume() * nozzleInaccuracyFactor;

        nozzle.setGlobalGrossVolume(nozzle.getGlobalGrossVolume() + grossVolume);
        nozzle.setGlobalNetVolume(nozzle.getGlobalNetVolume() + netVolume);
        nozzle.setCurrentRefuelingGrossVolume(nozzle.getCurrentRefuelingGrossVolume() + grossVolume);
        nozzle.setCurrentRefuelingNetVolume(nozzle.getCurrentRefuelingNetVolume() + netVolume);
    }

    @Override
    public void refuelTank(Tank tank, Double volume) {
        tank.setFuelVolume(tank.getFuelVolume() + volume);
    }

    @Override
    public void spoilFuel(Tank tank, Double volume) {
        Double v = tank.getFuelVolume() - volume;
        tank.setFuelVolume(v > 0 ? v : 0);
    }

    @Override
    public void changePipelineFactor(Pipeline pipeline, Pipeline.PipelineFactor pipelineFactor) {
        pipeline.setPipelineFactor(pipelineFactor);
    }

    @Override
    public void changeNozzleFactor(Nozzle nozzle, Nozzle.NozzleFactor nozzleFactor) {
        nozzle.setNozzleFactor(nozzleFactor);
    }

    @Override
    public void changeTankTemperature(Tank tank, Double changeValue) {
        double newTemp = tank.getTemperature() + changeValue;
        if (newTemp >= MIN_TEMP_BOUND && newTemp <= MAX_TEMP_BOUND) {
            tank.setFuelVolume(FuelUtils.volumeChangedTemperature(tank.getFuelVolume(), tank.getTemperature(), newTemp));
            tank.setTemperature(newTemp);
        }
    }

    public static final class Builder {

        Set<Identifier> identifiers = new HashSet<>();
        Map<Identifier, Tank> tanks = new HashMap<>();
        Map<Identifier, Nozzle> nozzles = new HashMap<>();
        List<Measurable> measurableList = new ArrayList<>();

        public static ServiceStationManagerImpl.Builder aServiceStationManagerImpl() {
            return new ServiceStationManagerImpl.Builder();
        }

        private Builder() {

        }

        public Builder addTank(Tank tank) {
            if (identifiers.contains(tank.getIdentifier())) {
                throw new IllegalArgumentException();
            }
            identifiers.add(tank.getIdentifier());
            tanks.put(tank.getIdentifier(), tank);
            measurableList.add(tank);
            return this;
        }

        public Builder addNozzle(Nozzle nozzle) {
            if (identifiers.contains(nozzle.getIdentifier())) {
                throw new IllegalArgumentException();
            }
            identifiers.add(nozzle.getIdentifier());
            nozzles.put(nozzle.getIdentifier(), nozzle);
            measurableList.add(nozzle);
            return this;
        }

        public Builder addPipeline(Pipeline pipeline, Identifier tank, Identifier nozzle) {
            if (!tanks.containsKey(tank) && !tanks.containsKey(nozzle)) {
                throw new IllegalArgumentException();
            }
            Nozzle nozzleObject = nozzles.get(nozzle);
            pipeline.setTank(tanks.get(tank));
            pipeline.setNozzle(nozzleObject);
            nozzleObject.setPipeline(pipeline);
            return this;
        }

        public ServiceStationManagerImpl build() {
            ServiceStationManagerImpl obj = new ServiceStationManagerImpl();
            obj.tanks = tanks;
            obj.nozzles = nozzles;
            obj.measurableList = measurableList;
            return obj;
        }

    }
}
