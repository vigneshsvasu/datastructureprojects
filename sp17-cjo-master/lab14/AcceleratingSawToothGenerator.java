import lab14lib.Generator;
/**
 * Created by vigneshvasu on 4/27/17.
 */
public class AcceleratingSawToothGenerator implements Generator{
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        if (state % period == 0 && state != 0) {
            state = (state + 1);
            double per = this.period * factor;
            this.period = (int) per;
            state = 0;
            return -1;
        }
        double slope = 2 / (double) this.period;
        double stateDub = (double) state % this.period;
        double returnVal = slope * stateDub - 1;
        state = (state + 1);
        return returnVal;
    }
}
