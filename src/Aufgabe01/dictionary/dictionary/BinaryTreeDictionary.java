// O. Bittel
// 22.09.2022
package Aufgabe01.dictionary.dictionary;

import java.util.Iterator;

import org.w3c.dom.Node;

/**
 * Implementation of the Dictionary interface as AVL tree.
 * <p>
 * The entries are ordered using their natural ordering on the keys, 
 * or by a Comparator provided at set creation time, depending on which constructor is used. 
 * <p>
 * An iterator for this dictionary is implemented by using the parent node reference.
 * 
 * @param <K> Key.
 * @param <V> Value.
 */
public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {


    private static class MinEntry<K, V> {
        private K key;
        private V value;
    }

    @Override
    public V insert(K key, V value) {
        this.root = insertR(key, value, this.root);
        if (this.root != null) {
            this.root.parent = null;
        }
        return this.oldValue;
    }

    private Node<K,V> insertR(K key, V value, Node<K,V> p) {
        if (p == null) {
            p = new Node<>(key, value);
            this.oldValue = null;
            this.size++;
        } else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }
        } else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }
        } else {
            this.oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;
    }

    @Override
    public V search(K key) {
        return searchR(key, this.root);
    }

    private V searchR(K key, Node<K, V> p) {
        if (p == null) return null;
        if (key.compareTo(p.key) < 0) return searchR(key, p.left);
        if (key.compareTo(p.key) > 0) return searchR(key, p.right);
        return p.value;
    }

    @Override
    public V remove(K key) {
        this.root = removeR(key, this.root);
        return this.oldValue;
    }

    private Node<K,V> removeR(K key, Node<K,V> p) {
        if (p == null) {
            this.oldValue = null;
        } else if (key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }
        } else if (key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }
        } else if (p.left == null || p.right == null) {
            // p loeschen + ein Kind
            this.oldValue = p.value;
            if (p.left != null) {
                p.left.parent = p.parent;
                p = p.left;
            } else if (p.right != null) {
                p.right.parent = p.parent;
                p = p.right;
            } else {
                p = null;
            }
            this.size--;
        } else {
            // p loeschen + 2 Kinder
            MinEntry<K, V> min = new MinEntry<>();
            p.right = getRemMinR(p.right, min);
            if (p.right != null) {
                p.right.parent = p;
            }
            this.oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
            this.size--;
        }
        return p;
    }

    /**
     * loescht im Baum p den Knoten mit kleinstem Schluessel und liefert Schluessel und Daten
     * des geloeschten Knotens ueber min zurueck.
     * @param p Baum
     * @param min HilfsdatenTyp
     * @return neuer Baum
     */
    private Node<K,V> getRemMinR(Node<K,V> p, MinEntry<K,V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else {
            p.left = getRemMinR(p.left, min);
        }
        return p;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BTDIterator();
    }

    private class BTDIterator implements Iterator<Entry<K, V>> {
        private Node<K,V> current;
        private int step;

        public BTDIterator() {
            this.step = 0;
        }

        @Override
        public boolean hasNext() {
            return step < size();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                return null;
            }
            if (this.step == 0 && root != null) {
                this.current = leftMostDescendant(root);
            } else if (this.current.right != null) {
                this.current = leftMostDescendant(this.current.right);
            } else {
                this.current = parentOfLeftMostAncestor(current);
            }
            this.step++;
            return new Entry<>(this.current.key, this.current.value);
        }
    }

    private Node<K, V> leftMostDescendant(Node<K, V> p) {
        assert p != null;
        while(p.left != null) {
            p = p.left;
        }
        return p;
    }

    private Node<K, V> parentOfLeftMostAncestor(Node<K, V> p) {
        assert p != null;
        while(p.parent != null && p.parent.right == p) {
            p = p.parent;
        }
        return p.parent;
    }

    private int getHeight(Node<K, V> p) {
        return p == null ? -1 : p.height;
    }

    private int getBalance(Node<K, V> p) {
        return p == null ? 0 : getHeight(p.right) - getHeight(p.left);
    }

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null) {
            return null;
        }
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) { // left too high
            if (getBalance(p.left) <= 0) { // left left
                p = rotateLeft(p);
            } else {
                p = rotateLeftRight(p);
            }
        } else if (getBalance(p) == 2) { // right too high
           if (getBalance(p.right) >= 0) { // right right
                p = rotateRight(p);
            } else {
                p = rotateRightLeft(p);
            }
        }
        return p;
    }

    private Node<K, V> rotateRight(Node<K, V> p) {
        assert p.left != null;
        Node<K, V> q = p.left;
        q.parent = p.parent;
        p.left = q.right; // p gets right child of q
        if (p.left != null) {
            p.left.parent = p;
        }
        p.right = p; // p becomes right child of q
        // height update:
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeft(Node<K, V> p) {
        assert p.right != null;
        Node<K, V> q = p.right;
        q.parent = p.parent;
        p.right = q.left; // p gets left child of q
        if (p.right != null) {
            p.right.parent = p;
        }
        q.left = p; // p becomes left child of q
        // height update:
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node<K, V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        if (p.left != null) {
            p.left.parent = p;
        }
        return rotateRight(p);
    }

    private Node<K, V> rotateRightLeft(Node<K, V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        if (p.right != null) {
            p.right.parent = p;
        }
        return rotateLeft(p);
    }

    static private class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }
    }
    
    private Node<K, V> root = null;
    private int size = 0;
    private V oldValue = null;
    
    // ...

	/**
	 * Pretty prints the tree
	 */
	public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);
        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key.toString()));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }
        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }
}
