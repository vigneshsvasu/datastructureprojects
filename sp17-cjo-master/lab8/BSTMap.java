package lab8;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class BSTMap<K, V> extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    private class Node{
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node (K key, V val, int size){
            this.K = key;
            this.V = val;
            this.size = size;
        }
    }

    public int size(){
        return size(root);
    }

    public int size(Node x){
        if (x == null){
            return 0;
        }
        return x.size();
    }

    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(K key) {
        return get(root, key);
    }

    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }



}
