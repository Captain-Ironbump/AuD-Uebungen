// O. Bittel;
// 30.07.2024

package Aufgabe02.aufgabe2.aufgabe2.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;


/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	private final DirectedGraph<V> myGraph;
	private Set<V> visited;
	private Stack<V> path;
	private Set<V> nodeInPath;
	private final List<V> preOrder = new LinkedList<>();
	private final List<V> postOrder = new LinkedList<>();

	/**
	 * Führt eine topologische Sortierung für g mit Tiefensuche durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
        this.myGraph = g;
		this.visited = new TreeSet<>();
		this.path = new Stack<>();
		this.nodeInPath = new TreeSet<>();

		for (V v : g.getVertexSet()) {
			if (!this.visited.contains(v))
				searchDirectedCycle(v);
		}
		this.ts = invertList(this.postOrder);
    }
    
	private void searchDirectedCycle(V v) {
		this.visited.add(v);
		this.path.push(v);
		this.nodeInPath.add(v);
		this.preOrder.add(v);

		for (V w : this.myGraph.getSuccessorVertexSet(v)) {
			if (!visited.contains(w)) {
				searchDirectedCycle(w);
			} else if (this.nodeInPath.contains(w)) {
				throw new IllegalArgumentException("Zyklus gefunden!");
			}
		}
		this.path.pop();
		this.nodeInPath.remove(v);
		this.postOrder.add(v);
	}
	
	public List<V> invertList(List<V> list) {
		List<V> invertedList = new LinkedList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			invertedList.add(list.get(i));
		}
		return invertedList;
	}

	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste, falls topologsche Sortierung möglich ist, sonst null.
	 */
	public List<V> topologicalSortedList() {
        return ts.isEmpty() ? null : Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 3);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		//g.addEdge(7, 1); // Kante führt zu Zyklus

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println("Topologische Sortierung:");
			System.out.println(ts.topologicalSortedList()); // [2, 1, 3, 5, 4, 6, 7]
		}
	}

	private static void test2() {
		// Morgendliches Anziehen:
		DirectedGraph<String> anziehGraph = new AdjacencyListDirectedGraph<>();
		anziehGraph.addEdge("Socken", "Schuhe");
		anziehGraph.addEdge("Unterhose", "Hose");
		anziehGraph.addEdge("Hose", "Schuhe");
		anziehGraph.addEdge("Hose", "Gürtel");
		anziehGraph.addEdge("Unterhemd", "Hemd");
		anziehGraph.addEdge("Hemd", "Pulli");
		anziehGraph.addEdge("Pulli", "Mantel");
		anziehGraph.addEdge("Gürtel", "Mantel");
		anziehGraph.addEdge("Mantel", "Schal");
		anziehGraph.addEdge("Schuhe", "Handschuhe");
		anziehGraph.addEdge("Schal", "Handschuhe");
		anziehGraph.addEdge("Mütze", "Handschuhe");

		TopologicalSort<String> ts = new TopologicalSort<>(anziehGraph);

		if (ts.topologicalSortedList() != null) {
			System.out.println("Topologische Sortierung:");
			System.out.println(ts.topologicalSortedList());
		}
	}
}
