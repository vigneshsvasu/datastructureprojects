import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
<<<<<<< HEAD
        Queue<Queue<Item>> totalQueue = new Queue<Queue<Item>>();
        while (!items.isEmpty()) {
            Queue<Item> newOne = new Queue<Item>();
            newOne.enqueue(items.dequeue());
            totalQueue.enqueue(newOne);
        }
        return totalQueue;
=======
        // Your code here!
        return null;
>>>>>>> 1c0df392fa29a10a01640a01900abcf4fb511fb7
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
<<<<<<< HEAD
        Queue<Item> returnQueue = new Queue<Item>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            returnQueue.enqueue(getMin(q1, q2));
        }
        if (q1.isEmpty() && !q2.isEmpty()) {
            while (!q2.isEmpty()) {
                returnQueue.enqueue(q2.dequeue());
            }
        }
        if (!q1.isEmpty() && q2.isEmpty()) {
            while (!q1.isEmpty()) {
                returnQueue.enqueue(q1.dequeue());
            }
        }
        System.out.println(returnQueue.toString());
        return returnQueue;
=======
        // Your code here!
        return null;
>>>>>>> 1c0df392fa29a10a01640a01900abcf4fb511fb7
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
<<<<<<< HEAD
        if (items.size() <= 1) {
            return items;
        }
        Queue<Item> firstHalf = new Queue<Item>();
        Queue<Item> secondHalf = new Queue<Item>();
        while (!items.isEmpty()) {
            firstHalf.enqueue(items.dequeue());
            if (!items.isEmpty()) {
                secondHalf.enqueue(items.dequeue());
            }
        }
        mergeSort(firstHalf);
        mergeSort(secondHalf);
        Queue<Item> x = mergeSortedQueues(firstHalf, secondHalf);
        return x;
    }

    public static void main(String[] args) {
        Queue<Integer> newQueue = new Queue<Integer>();
        newQueue.enqueue(5);
        newQueue.enqueue(2);
        newQueue.enqueue(7);
        newQueue.enqueue(9);
        newQueue.enqueue(2);
        newQueue.enqueue(1);
        Queue<Integer> sorted = mergeSort(newQueue);
        System.out.println(newQueue);
        System.out.println(sorted);

=======
        // Your code here!
        return items;
>>>>>>> 1c0df392fa29a10a01640a01900abcf4fb511fb7
    }
}
