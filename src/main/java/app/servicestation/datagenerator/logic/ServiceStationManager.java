package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Nozzle;
import app.servicestation.datagenerator.model.Pipeline;
import app.servicestation.datagenerator.model.Tank;

public interface ServiceStationManager extends Reportable{

    void startRefueling(Nozzle nozzle);
    void stopRefueling(Nozzle nozzle);
    void deliverFuel(Nozzle nozzle);
    void refuelTank(Tank tank, Double volume);

    void spoilFuel(Tank tank, Double volume);
    void changePipelineFactor(Pipeline pipeline, Pipeline.PipelineFactor pipelineFactor);
    void changeNozzleFactor(Nozzle nozzle, Nozzle.NozzleFactor nozzleFactor);
    void changeTankTemperature(Tank tank, Double changeValue);

    Tank getTank(Identifier identifier);
    Nozzle getNozzle(Identifier identifier);
}
