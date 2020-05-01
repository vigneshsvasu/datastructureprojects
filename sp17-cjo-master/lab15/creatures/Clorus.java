package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

/**
 * Created by vigneshvasu on 5/3/17.
 */
public class Clorus extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
        this.energy = this.energy() + c.energy();
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        this.energy = this.energy() - .03;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        this.energy = this.energy() - .01;
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Clorus replicate() {
        this.energy= this.energy() / 2.0;
        double val = this.energy;
        return new Clorus(val);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        boolean x = false;
        ArrayList<Direction> list = new ArrayList<>();
        for (Direction s: neighbors.keySet()) {
            if (neighbors.get(s).name() == "plip") {
                x = true;
                list.add(s);
            }
        }
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        else if (x) {
            int value = list.size();
            int otherVal = (int) Math.random() * value;
            return new Action(Action.ActionType.ATTACK, list.get(otherVal));
        }
        else if (this.energy() >= 1.0) {
            Direction y = empties.get(0);
            return new Action(Action.ActionType.REPLICATE, y);
        }
        int value = empties.size();
        int otherVal = (int) Math.random() * value;
        return new Action(Action.ActionType.ATTACK, empties.get(otherVal));
    }
}
