package app.servicestation.datagenerator.simulation.param;

import java.util.Random;

public class StandardProbabilityParameterDef implements ParameterDef<Boolean> {

    private static final Random rand = new Random();
    private Double threshold;

    public StandardProbabilityParameterDef(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Boolean generate() {
        return rand.nextDouble() < threshold;
    }
}
