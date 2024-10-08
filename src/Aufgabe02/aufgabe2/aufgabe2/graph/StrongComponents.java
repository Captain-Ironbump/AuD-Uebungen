// O. Bittel;
// 22.02.2017

package Aufgabe02.aufgabe2.aufgabe2.graph;

import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert jede Komponente die zughörigen Knoten. 
	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	
	// Anzahl der Komponenten:
	private int numberOfComp = 0;
	
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		// ...
		DepthFirstOrder<V> dfs = new DepthFirstOrder<>(g);
		List<V> postOrder = dfs.postOrder();
		List<V> postOrderInverted = new ArrayList<>();
		for (int i = postOrder.size() - 1; i >= 0; i--) {
			V v = postOrder.get(i);
			postOrderInverted.add(v);
		}

		DirectedGraph<V> gi = g.invert();
		visitDF(postOrderInverted, gi);
		this.numberOfComp = this.comp.size();
	}

	private void visitDF(List<V> visiting, DirectedGraph<V> g) {
		Set<V> visited = new TreeSet<>();
		int index = 0;
		for (V v : visiting) {
			if (!visited.contains(v)) {
				this.comp.put(index, new TreeSet<>());
				visitDF(v, g, visited, index);
				index++;
			}
		}
	}

	private void visitDF(V v, DirectedGraph<V> g, Set<V> visited, int index) {
		visited.add(v);
		this.comp.get(index).add(v);
		for (V w : g.getSuccessorVertexSet(v)) {
			if (!visited.contains(w)) {
				visitDF(w, g, visited, index);
			}
		}
	}
	
	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return numberOfComp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var e : this.comp.entrySet()) {
			sb.append("Component " + e.getKey() + ": " + e.getValue() + "\n");
		}
		return sb.toString();
	}
	
		
	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7, 
        	// Component 1: 8, 
            // Component 2: 1, 2, 3, 
            // Component 3: 4, 
	}
}


// Der reduzierte Graph muss azyklisch sein (warum?).
// Antwort: weil sonst die Komponenten nicht streng wären.
// 