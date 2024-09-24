package Aufgabe04.src;

import Aufgabe04.src.interfaces.IUnionFind;
import java.util.HashMap;

public class UnionFind<T> implements IUnionFind<T> {
    private HashMap<T, T> p; // Elternfeld (Feld mit Elternknoten)
    private HashMap<T, Integer> heightField; // Höhenfeld (Feld mit Höhen der Bäume)

    public UnionFind(T[] elements) {
        this.p = new HashMap<>();
        this.heightField = new HashMap<>();
        // init p and heightField
        for (T e : elements) {
            this.p.put(e, null);
            this.heightField.put(e, 0);
        }
    }

    @Override
    public T find(T e) {
        while (this.p.get(e) != null) {
            e = this.p.get(e);
        }
        return e;
    }

    @Override
    public void union(T s1, T s2) {
        if (p.get(s1) != null || p.get(s2) != null) {
            return;
        }
        if (s1 == s2) {
            return;
        }

        if (this.heightField.get(s1) < this.heightField.get(s2)) {
            this.p.put(s1, s2);
        } else {
            if (this.heightField.get(s1) == this.heightField.get(s2)) {
                this.heightField.put(s1, this.heightField.get(s1) + 1);
            }
            this.p.put(s2, s1);
        }
        // remove the Node from the HashMap
        T node = this.find(s2);
        if (node != s2) {
            this.heightField.remove(s2);
        } else {
            this.heightField.remove(s1);
        }
    }

    public int getHeight(T e) {
        return this.heightField.get(e);
    }

    public void printHeightField() {
        System.out.println("HeightField: ");
        for (T e : this.heightField.keySet()) {
            System.out.println("Element: " + e + " Height: " + this.heightField.get(e));
        }
    }

    public void printP() {
        System.out.println("P: ");
        for (T e : this.p.keySet()) {
            System.out.println("Element: " + e + " Parent: " + this.p.get(e));
        }
    }

    public static void main(String[] args) {
        UnionFind<Integer> uf = new UnionFind<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        Integer s1 = uf.find(1);
        System.out.println("Repräsentant von 1: " + s1);
        int s1Height = uf.getHeight(s1);
        System.out.println("Höhe von 1: " + s1Height);
        uf.printP();
        uf.printHeightField();

        uf.union(1, 2);
        uf.union(3, 4);
        
        uf.printP();
        uf.printHeightField();

        uf.union(1, 3);
        uf.printP();
        uf.printHeightField();
    }
}
