package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private Collection<Node>[] buckets;
    private int size;

    public MyHashMap() {
        buckets = createTable(16); // 初始化buckets数组，大小为16
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }


    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
    }


    /* Instance Variables */
    // You should probably define some more!

    /** Constructors */


    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }


    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param size the size of the table to create
     */
    private Collection<Node>[] createTable(int size) {
        Collection<Node>[] table = new Collection[size];
        for (int i = 0 ; i < size ; i++){
            table[i] = new ArrayList<>();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        buckets = createTable(buckets.length);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        for (Node node : buckets[index]){
            if (node.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        for (Node node : buckets[index]){
            if (node.key.equals(key)){
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[index].add(createNode(key, value));
        size++;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }


    @Override
    public V remove(K key) {
        int index = hash(key);
        Iterator<Node> iterator = buckets[index].iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                V value = node.value;
                iterator.remove();
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        Iterator<Node> iterator = buckets[index].iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key) && node.value.equals(value)) {
                iterator.remove();
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int hash(K key){
        return Math.floorMod(key.hashCode(), buckets.length);
    }


}
