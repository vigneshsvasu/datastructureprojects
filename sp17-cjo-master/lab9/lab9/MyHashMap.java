package lab9;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by vigneshvasu on 3/16/17.
 */
public class MyHashMap<K,V> implements Map61B<K, V>{
    private int capacity = 10;
    private double loadFactor;
    public Entry<K,V>[] table;
    public HashSet<K> keysSet;

    public static class Entry<K, V>{
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Entry getNext() {
            return this.next;
        }

        public K getKey() {
            return this.key;
        }
    }
    public MyHashMap() {
        table = new Entry[capacity];
    }
    public MyHashMap(int cap){
        capacity = cap;
        loadFactor = 0.75;
        table = new Entry[capacity];
    }
    public MyHashMap(int cap, double fact) {
        capacity = cap;
        loadFactor = fact;
        table = new Entry[capacity];
    }
    public V remove(K key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    private int hash(K key) {
        return (key.hashCode() % capacity;
    }

    public V get(K key) {
        int hash = hash(key);
        return table[hash].value;

    }

    public boolean containsKey(K key){
        if (contains(table, key)) {
            return true;
        }
        return false;
    }

    public int size() {
        int count = 0;
        for (Entry i : table) {
            if (i.key != null) {
                count++;
            }
        }
        return count;
    }

    public void put (K key, V value) {
        if (key == null){
            return;
        }
        int hashVal = hash(key);
        Entry newEntry = new Entry
    }

    public static boolean contains(Entry[] array, K val) {

        boolean result = false;

        for(Entry i : array) {
            if (i.key == val) {
                result = true;
            }
        }

        return result;
    }

    public Set<K> keySet() {
        return keysSet;
    }

    public void rezie() {
        capacity *= 2;
        Entry<K,V>[] newTable = (Entry<K, V>[]) new Entry[capacity];
        for (Entry<K,V> i : table) {
            while (i != null) {
                Entry<K,V> next = i.getNext();
                int hash = hash(i.getKey());
            }
        }
    }
}
