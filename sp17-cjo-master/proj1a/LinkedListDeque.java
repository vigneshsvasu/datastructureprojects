/**
 * Created by vigneshvasu on 1/30/17.
 */
public class LinkedListDeque<Item> {
    /** Creates an empty list. */
    private class GenNode {
        private Item item;
        private GenNode next;
        private GenNode prev;
        private GenNode last;

        private GenNode(Item i, GenNode n) {
            item = i;
            next = n;
            prev = null;
            last = null;
        }

        public GenNode next() {
            return this.next;
        }

        public GenNode prev() {
            return this.prev;
        }
    }

    private GenNode sentinel;
    private int size;




    public LinkedListDeque() {
        sentinel = new GenNode(null, null);
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        sentinel.last = sentinel;

    }


    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        GenNode p =  new GenNode(x, null);
        sentinel.last.next = p;
        sentinel.last.next.prev = sentinel.last;
        sentinel.last = sentinel.last.next;
        sentinel.last.next = sentinel;
        sentinel.prev = sentinel.last;
        size += 1;

    }

    public void addFirst(Item x) {
        sentinel.next = new GenNode(x, sentinel.next);
        sentinel.next.prev = sentinel;
        sentinel.next.next.prev = sentinel.next;
        if (size == 0) {
            sentinel.last = sentinel.next;
            sentinel.last.next = sentinel;
        }
        size = size + 1;

    }

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Item p = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        if (size == 1) {
            sentinel.last = sentinel;
        }
        size = size - 1;
        return p;
    }

    public Item removeLast() {
        Item p = sentinel.last.item;
        sentinel.last = sentinel.last.prev;
        sentinel.last.prev = sentinel.prev;
        sentinel.last.next = sentinel;
        sentinel.prev = sentinel.last;
        size = size - 1;
        return p;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void printDeque() {
        GenNode p = sentinel;
        while (p.next.item != null) {
            System.out.print(p.next.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    /** Returns the item from the back of the list. */
    private Item getLast() {
        return sentinel.last.item;
    }

    /** Gets the ith item in the list (0 is the front). */
    public Item getRecursive(int i) {
        GenNode other = sentinel.next;
        if (i > size()) {
            return null;
        }
        return helper(other, i);


    }
    private Item helper(GenNode x, int index) {
        if (index == 0) {
            return x.item;
        } else {
            return helper(x.next, index - 1);
        }
    }

    public Item get(int i) {
        int val = 0;
        GenNode u = sentinel.next;
        while (val < i) {
            u = u.next;
            val = val + 1;
        }
        return u.item;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */



}
