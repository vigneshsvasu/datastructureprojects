import lab14lib.Generator;
/**
 * Created by vigneshvasu on 4/27/17.
 */
public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        int weirdState;
        if (state % period == 0) {
            state = (state + 1);
            weirdState = state & (state >> 3) & (state >> 8);
            return -1;
        }
        state = (state + 1);
        weirdState = state & (state >> 3) & (state >> 8);
        double slope = 2 / (double) period;
        double stateDub = (double) weirdState % period;
        double returnVal = slope * stateDub - 1;
        return returnVal;
    }
}
