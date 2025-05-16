package com.aston.task1;

public class CoolHashMap <K, V>{
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public CoolHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public CoolHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public CoolHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        this.loadFactor = loadFactor;
        this.table = new Node[initialCapacity];
        this.threshold = (int)(initialCapacity * loadFactor);
    }

    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    public V put(K key, V value) {
        if (key == null) {
            return putForNullKey(value);
        }

        int hash = hash(key);
        int index = (table.length - 1) & hash;

        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && (node.key == key || key.equals(node.key))) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        addNode(hash, key, value, index);
        return null;
    }

    private V putForNullKey(V value) {
        Node<K, V> node = table[0];
        while (node != null) {
            if (node.key == null) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        addNode(0, null, value, 0);
        return null;
    }

    private void addNode(int hash, K key, V value, int index) {
        Node<K, V> newNode = new Node<>(hash, key, value, table[index]);
        table[index] = newNode;

        if (++size > threshold) {
            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity << 1;
        threshold = (int)(newCapacity * loadFactor);

        Node<K, V>[] newTable = new Node[newCapacity];

        for (Node<K, V> kvNode : table) {
            Node<K, V> node = kvNode;
            while (node != null) {
                Node<K, V> next = node.next;
                int newIndex = (newCapacity - 1) & node.hash;
                node.next = newTable[newIndex];
                newTable[newIndex] = node;
                node = next;
            }
        }

        table = newTable;
    }

    public V get(K key) {
        if (key == null) {
            Node<K, V> node = table[0];
            while (node != null) {
                if (node.key == null) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        }

        int hash = hash(key);
        int index = (table.length - 1) & hash;

        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && (node.key == key || key.equals(node.key))) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) {
            return removeNullKey();
        }

        int hash = hash(key);
        int index = (table.length - 1) & hash;

        Node<K, V> prev = null;
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.hash == hash && (node.key == key || key.equals(node.key))) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }

        return null;
    }

    private V removeNullKey() {
        Node<K, V> prev = null;
        Node<K, V> node = table[0];

        while (node != null) {
            if (node.key == null) {
                if (prev == null) {
                    table[0] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }

        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
        threshold = (int)(DEFAULT_CAPACITY * loadFactor);
    }
}
