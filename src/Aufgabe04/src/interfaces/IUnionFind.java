package Aufgabe04.src.interfaces;

public interface IUnionFind<T> {
    /***
     * Liefert diejenige Menge zurück, in der sich das Element e befindet.
     * @param e Element e
     * @return Repraesentant der Menge, in der sich e befindet 
     */
    T find(T e);
    /**
     * Vereinigt die zwei Mengen, in denen sich die Representanten s1 und s2 befinden.
     * Der Repraesentant der neuen Menge ist der Repraesentant, welcher der Hoeheren Menge angehoert.
     * @param s1 Repraesentant der Menge 1
     * @param s2 Repraesentant der Menge 2
     */
    void union(T s1, T s2);
}
