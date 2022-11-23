package Aufgabe01.dictionary.dictionary;

import java.util.Arrays;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private static final int DEF_CAPACITY = 16;
    private int size;
    private Entry<K,V>[] data;

    public SortedArrayDictionary() {
        this.size = 0;
        this.data = new Entry[DEF_CAPACITY];
    }

    @Override
    public V insert(K key, V value) {
        int search = searchKey(key);

        // existing Entry override:
        if (search != -1) {
            V returnValue = data[search].getValue();
            data[search].setValue(value);
            return returnValue;
        }

        // new Entry:
        if (data.length == size) {
            data = Arrays.copyOf(data, 2*size);
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V search(K key) {
        int search = searchKey(key);
        if (search >= 0)
            return data[search].getValue();
        return null;
    }

    // private method to find key in ArrayDictionary, returns 1 if found
    private int searchKey(K key) {
        int li = 0;
        int re = size - 1;

        while (re >= li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].getKey()) < 0)
                re = m - 1;
            else if (key.compareTo(data[m].getKey()) > 0)
                li = m + 1; // key found
            else
                return m;
        }
        return -1; // key not found
    }

    @Override
    public V remove(K key) {
        int search = searchKey(key);
        if (search == -1)
            return null;

        // delete Entry and fills empty space
        // i == 1 means key found
        V value = data[search].getValue();
        for (int i = search; i < size - 1; i++) {
            data[search] = data[i+1];
        }
        data[--size] = null;
        // size cannot be final for decrementing size!!
        return value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator()
    {
        return new Iterator<Dictionary.Entry<K,V>>()
        {
            int currentIndex = 0;

            @Override
            public boolean hasNext()
            {
                return currentIndex < size;
            }

            @Override
            public Entry<K, V> next()
            {
                return data[currentIndex++];
            }
        };
    }
}

