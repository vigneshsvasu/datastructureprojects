/**
 * Created by vigneshvasu on 1/30/17.
 */
public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int firstVal = 4;
    private int lastVal = 5;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    public void addFirst(Item item) {
        size = size + 1;
        if (size == items.length) {
            resize(size * 2);
            addFirst(item);
        } else if (firstVal == 0) {
            items[firstVal] = item;
            firstVal = items.length - 1;
        } else {
            items[firstVal] = item;
            firstVal = firstVal - 1;
        }

    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        size = size + 1;
        if (size == items.length) {
            resize(size * 2);
            addLast(x);
        } else if (lastVal == items.length - 1) {
            items[lastVal] = x;
            lastVal = 0;
        } else {
            items[lastVal] = x;
            lastVal = lastVal + 1;
        }
    }

    /** Returns the item from the back of the list. */
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public Item removeLast() {
        if (lastVal == 0) {
            lastVal = items.length - 1;
        } else {
            lastVal = lastVal - 1;
        }
        Item x = items[lastVal];
        items[lastVal] = null;
        size = size - 1;
        return x;
    }

    public Item removeFirst() {
        if (firstVal == items.length - 1) {
            firstVal = 0;
        }
        firstVal = firstVal + 1;
        Item x = items[firstVal];
        items[firstVal] = null;
        size = size - 1;
        return x;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        if (firstVal > lastVal) {
            int count = 0;
            int x = firstVal;
            while (x < items.length) {
                System.out.println(items[x]);
                x++;
                count++;
            }
            int y = lastVal;
            while (y < (size - count)) {
                System.out.println(items[y]);
                y++;
            }
        }
        for (int g = firstVal; g < lastVal; g++) {
            if (items[g] != null) {
                System.out.println(items[g]);
            }

        }
    }


}
