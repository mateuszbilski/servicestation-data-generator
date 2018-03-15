package app.servicestation.datagenerator.test;

import app.servicestation.datagenerator.endpoint.ConsoleEndpoint;
import app.servicestation.datagenerator.endpoint.WebSocketEndpoint;
import app.servicestation.datagenerator.logic.*;
import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Nozzle;
import app.servicestation.datagenerator.model.Pipeline;
import app.servicestation.datagenerator.model.Tank;
import app.servicestation.datagenerator.simulation.def.FuelSpoilageEffectDef;
import app.servicestation.datagenerator.simulation.def.RefuelingEffectDef;
import app.servicestation.datagenerator.simulation.def.TankTemperatureChangeEffectDef;
import app.servicestation.datagenerator.simulation.param.DoubleRandomRangeParameterDef;
import app.servicestation.datagenerator.simulation.param.RandomRangeParameterDef;
import app.servicestation.datagenerator.simulation.param.StandardProbabilityParameterDef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private ServiceStationManager serviceStationManager;
    private EffectManager effectManager;
    private ReportManager reportManager;
    private Long simulationTime = 0l;
    private ScheduledExecutorService executorService;

    public void buildModel() {
        serviceStationManager = ServiceStationManagerImpl.Builder.aServiceStationManagerImpl()
                .addTank(
                        Tank.Builder.aTank()
                                .withIdentifier(new Identifier("tank1"))
                                .withFuelVolume(15000.0)
                                .withTemperature(18.0)
                                .withWaterVolume(1000.0)
                                .build()
                )
                .addNozzle(
                        Nozzle.Builder.aNozzle()
                                .withIdentifier(new Identifier("nozzle1"))
                                .withNozzleFactor(new Nozzle.NozzleFactor(1 - 1e-6, 1 - 1e-6, 1 + 1e-6))
                                .build()
                )
                .addNozzle(
                        Nozzle.Builder.aNozzle()
                                .withIdentifier(new Identifier("nozzle2"))
                                .withNozzleFactor(new Nozzle.NozzleFactor(1 - 1e-6, 1 - 1e-6, 1 + 1e-6))
                                .build()
                )
                .addPipeline(
                        Pipeline.Builder.aPipeline()
                                .withThroughputCapacity(1.25)
                                .withPipelineFactor(new Pipeline.PipelineFactor(1 - 1e-6))
                                .build(),
                        new Identifier("tank1"),
                        new Identifier("nozzle1")
                )
                .addPipeline(
                        Pipeline.Builder.aPipeline()
                                .withThroughputCapacity(1.4)
                                .withPipelineFactor(new Pipeline.PipelineFactor(1 - 1e-6))
                                .build(),
                        new Identifier("tank1"),
                        new Identifier("nozzle2")
                )
                .build();

        effectManager = EffectManagerImpl.Builder.aEffectManagerImpl()
                .addEffectDef(
                        RefuelingEffectDef.Builder.aRefuelingEffectDef()
                                .withIdentifier(new Identifier("nozzle1"))
                                .withDuration(() ->  5)
                                .withEventProbability(new StandardProbabilityParameterDef(0.15))
                                .build()
                )
                .addEffectDef(
                        RefuelingEffectDef.Builder.aRefuelingEffectDef()
                                .withIdentifier(new Identifier("nozzle2"))
                                .withDuration(() ->  5)
                                .withEventProbability(new StandardProbabilityParameterDef(0.20))
                                .build()
                )
                .addEffectDef(
                        FuelSpoilageEffectDef.Builder.aFuelSpoilageEffectDef()
                                .withIdentifier(new Identifier("tank1"))
                                .withDuration(() -> 60)
                                .withLeakageVolume(() -> 20.0)
                                .withEventProbability(new StandardProbabilityParameterDef(0.05))
                                .build()
                )
                .addEffectDef(
                        TankTemperatureChangeEffectDef.Builder.aTankTemperatureChangeEffectDef()
                                .withIdentifier(new Identifier("tank1"))
                                .withDuration(new RandomRangeParameterDef(40, 80))
                                .withEventProbability(new StandardProbabilityParameterDef(0.05))
                                .withTemperatureChangeValue(new DoubleRandomRangeParameterDef(-0.1, 0.1))
                                .build()
                )
                .build();

        reportManager = new ReportManagerImpl();
        reportManager.addEndpoint(new ConsoleEndpoint());
        reportManager.addEndpoint(new WebSocketEndpoint());
    }

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.buildModel();
        sim.executorService = Executors.newScheduledThreadPool(1);
        sim.executorService.scheduleWithFixedDelay(
                () -> {
                    if (++sim.simulationTime < 10000) {
                        sim.effectManager.processEffects(sim.serviceStationManager);
                        sim.reportManager.prepareReport(sim.serviceStationManager);
                    } else {
                        sim.executorService.shutdown();
                        sim.reportManager.removeAll();
                    }

                }, 0, 1, TimeUnit.SECONDS
        );
    }
}
