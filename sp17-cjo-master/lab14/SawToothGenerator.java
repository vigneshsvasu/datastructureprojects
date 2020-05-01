import lab14lib.Generator;

/**
 * Created by vigneshvasu on 4/27/17.
 */
public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        if (state % period == 0) {
            state = (state + 1);
            return -1;
        }
        state = (state + 1);
        double slope = 2 / (double) period;
        double stateDub = (double) state % period;
        double returnVal = slope * stateDub - 1;
        return returnVal;
    }
}
