import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by vigneshvasu on 2/8/17.
 */
public class TestArrayDeque1B {
    @Test
    public void test1() {

        ArrayDequeSolution<Integer> solDeque = new ArrayDequeSolution<Integer>();

        StudentArrayDeque<Integer> wrongDeque = new StudentArrayDeque<Integer>();
        OperationSequence newOp = new OperationSequence();
        DequeOperation dequeOp;
        for (int i = 0; i < 10; i++) {
            int val = StdRandom.uniform(0, 15);
            if (val > 7) {
                solDeque.addFirst(val);
                wrongDeque.addFirst(val);
                dequeOp = new DequeOperation("addFirst", val);
                newOp.addOperation(dequeOp);
            } else {
                solDeque.addLast(val);
                wrongDeque.addLast(val);
                dequeOp = new DequeOperation("addLast", val);
                newOp.addOperation(dequeOp);
            }
        }
        for (int k = 0; k < 5; k++) {
            int otherVal = StdRandom.uniform(0, 10);
            if (otherVal < 0) {
                solDeque.removeFirst();
                wrongDeque.removeFirst();
                dequeOp = new DequeOperation("removeFirst");
                newOp.addOperation(dequeOp);
            } else {
                solDeque.removeLast();
                wrongDeque.removeLast();
                dequeOp = new DequeOperation("removeLast");
                newOp.addOperation(dequeOp);
            }
        }
        solDeque.removeLast();
        wrongDeque.removeLast();
        newOp.addOperation(new DequeOperation("removeLast"));
        Integer actual = solDeque.removeLast();
        Integer expected = wrongDeque.removeLast();
        newOp.addOperation(new DequeOperation("removeLast"));
        assertEquals(newOp.toString(), expected, actual);

    }

}
