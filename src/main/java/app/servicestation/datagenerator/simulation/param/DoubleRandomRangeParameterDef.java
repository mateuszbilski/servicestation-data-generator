package app.servicestation.datagenerator.simulation.param;

import java.util.Random;

/**
 * Created by home on 9/10/2016.
 */
public class DoubleRandomRangeParameterDef implements ParameterDef<Double> {

    private static final Random rand = new Random();
    private Double origin;
    private Double bound;

    public DoubleRandomRangeParameterDef(double origin, double bound) {
        this.origin = origin;
        this.bound = bound;
    }

    @Override
    public Double generate() {
        return rand.nextDouble() * (bound - origin) + origin;
    }
}
