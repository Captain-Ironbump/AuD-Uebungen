package Aufgabe01.dictionary.dictionary;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K,V> implements Dictionary<K,V> {

    private LinkedList<Entry<K, V>>[] data;
    private int elements;
    private static final int loadFactor = 2;

    public HashDictionary(int m) {
        this.data = new LinkedList[m];
        if (!isPrim(m))
            do {
                m++;
            } while (!isPrim(m));
    }

    private boolean isPrim(int num) {
        if (num == 2 || num == 3)
            return true;

        if (num <= 1 || num % 2 == 0 || num % 3 == 0)
            return false;

        for (int i = 5; i * i <= num; i += 6)
        {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }

    private void raiseArraySizeAndMoveEntries() {
        LinkedList<Entry<K, V>>[] newArray = new LinkedList[doubleSize()];
        for (Entry<K, V> entry : this) {
            newArray[hash(entry.getKey())].add(entry);
        }
        this.data = newArray;
    }

    private int doubleSize() {
        int newSize = data.length * 2;
        while (!isPrim(newSize)) {
            newSize++;
        }
        return newSize;
    }

    private int hash(K key) {
        int adr = key.hashCode();
        int m = data.length;

        /* keyString = key.toString();
        for (int i = 0; i < keyString.length(); i++)
            adr = 31 * adr + keyString.charAt(i);*/

        if (adr < 0)
            adr = -adr;
        return adr % m;
    }

    @Override
    public V insert(K key, V value) {
        int adr = hash(key);
        if (data[adr] == null) {
            data[adr] = new LinkedList<Entry<K, V>>();
        }
        else {
            for (Entry<K, V> entry : data[adr]) {
                if (entry.getKey().equals(key)) {
                    V v = entry.getValue();
                    entry.setValue(value);
                    if (data[adr].size() > loadFactor)
                        raiseArraySizeAndMoveEntries();
                    return v;
                }
            }
        }
        data[adr].add(new Entry<>(key, value));
        this.elements++;
        return null;
    }

    @Override
    public V search(K key) {
        int adr = hash(key);
        if (data[adr] != null)
            for (Entry<K, V> entry : data[adr]) {
                if (entry.getKey().equals(key))
                    return entry.getValue();
            }
        return null;
    }


    @Override
    public V remove(K key) {
        int adr = hash(key);
        if (data[adr] != null) {
            for (Entry<K, V> entry : data[adr]) {
                if (entry.getKey().equals(key)) {
                    V v = entry.getValue();
                    data[adr].remove(entry);
                    this.elements--;
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.elements;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {

            private int currentIndex = 0;
            private int currentDataIndex = 0; // Primzahl


            @Override
            public boolean hasNext() {
                if (currentDataIndex < data.length) {
                    if (data[currentDataIndex] == null) {
                        currentDataIndex++;
                        return this.hasNext();
                    } else {
                        return currentIndex < data[currentDataIndex].size();
                    }
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                if (data[currentDataIndex] == null && ++currentDataIndex < data.length) {
                    return this.next();
                }

                Entry<K, V> entry = data[currentDataIndex].get(currentIndex++);

                if (currentIndex >= data[currentDataIndex].size()) {
                    currentDataIndex++;
                    currentIndex = 0;
                }

                return entry;
            }
        };
    }
}
