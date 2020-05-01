/** If you project is set up properly, this file should execute. 
* One thing you might consider is to try printing out the sequence of
* operations */
public class StudentArrayDequeLauncher {
    public static void main(String[] args) {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        OperationSequence newOp = new OperationSequence();
        DequeOperation dequeOp;
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                dequeOp = new DequeOperation("addLast", i);
                newOp.addOperation(dequeOp);
            } else {
                sad1.addFirst(i);
                dequeOp = new DequeOperation("addFirst", i);
                newOp.addOperation(dequeOp);
            }
        }

        sad1.printDeque();
        System.out.println(newOp.toString());
        /* Helpful challenge: Modify this file so that it outputs the list of
           operations as a String. Use the OperationSequence class. */
    }
} 
