package bstmap;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTMap <K extends Comparable<K> ,V> implements Map61B<K,V>{
    private Node root;
    private int size;

     private class Node {
         private K key;
         private V value;
         private Node left , right;

         public Node (K key , V value){
             this.key = key;
             this.value = value;
         }
     }
     public BSTMap(){
         root = null;
         size = 0;
     }


    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node node , K key){
         if (node == null){
             return null;
         }
         int compara = key.compareTo(node.key);
         if (compara == 0){
             return node.value;
         }
         if (compara < 0){
             return get(node.left , key);
         }else {
             return get(node.right , key);
         }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root , key , value);
    }

    private Node put(Node node , K key , V value){
         if (node == null){
             size++;
             return new Node (key,value);
         }
         int cmpara = key.compareTo(node.key);
         if (cmpara == 0){
             node.value = value;
         }else if (cmpara > 0){
             node.right = put (node.right , key , value);
         }else{
             node.left = put (node.left , key , value);
         }
         return node;
    }


    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySet(root, keys);
        return keys;
    }

    private void keySet(Node node, Set<K> keys) {
        if (node != null) {
            keys.add(node.key);
            keySet(node.left, keys);
            keySet(node.right, keys);
        }
    }

    @Override
    public V remove(K key) {
        return null;
    }


    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
