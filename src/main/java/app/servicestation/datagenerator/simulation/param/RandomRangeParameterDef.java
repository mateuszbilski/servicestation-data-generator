package app.servicestation.datagenerator.simulation.param;

import java.util.Random;

public class RandomRangeParameterDef implements ParameterDef<Integer> {

    private static final Random rand = new Random();
    private Integer origin;
    private Integer bound;

    public RandomRangeParameterDef(Integer origin, Integer bound) {
        this.origin = origin;
        this.bound = bound;
    }

    @Override
    public Integer generate() {
        return rand.nextInt(bound - origin) + origin;
    }
}
