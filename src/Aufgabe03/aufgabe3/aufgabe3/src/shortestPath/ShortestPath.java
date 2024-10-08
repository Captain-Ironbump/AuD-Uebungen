// O. Bittel;
// 26.09.2022

package Aufgabe03.aufgabe3.aufgabe3.src.shortestPath;

import Aufgabe02.aufgabe2.aufgabe2.graph.*;
import Aufgabe03.aufgabe3.aufgabe3.src.sim.SYSimulation;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; 		// Distanz für jeden Knoten
	Map<V,V> pred; 				// Vorgänger für jeden Knoten
	IndexMinPQ<V,Double> cand; 	// Kandidaten als PriorityQueue PQ
	// ...
	DirectedGraph<V> graph; // Graph
	Heuristic<V> heu; // Heuristik
	V start; // Startknoten zur Wegfindung
	V ziel; // Zielknoten zur Wegfindung
	boolean pathFound;
	
	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		dist = new HashMap<>();
		pred = new HashMap<>();
		cand = new IndexMinPQ<>();
		graph = g;
		heu = h;
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g) {
		// ...
		this.start = s;
		this.ziel = g;
		for (V v : graph.getVertexSet()) {
			this.dist.put(v, Double.POSITIVE_INFINITY);
			this.pred.put(v, null);
		}
		this.dist.put(s, 0.0);
		// Heuristik entscheidet welcher Algorithmus verwendet wird
		if (this.heu != null) { // A* Algorithmus 
			this.cand.add(s, 0 + this.heu.estimatedCost(s, g));
		} else { // Dijkstra Algorithmus
			this.cand.add(s, 0.0);
		}

		while (!this.cand.isEmpty()) {
			V v = this.cand.removeMin();
			// System.out.println("Besuche Knoten " + v.toString() + " mit d = " + this.dist.get(v));
			if (this.sim != null) {
				this.sim.visitStation((Integer) v, java.awt.Color.blue);
			}
			if (v.equals(g)) {
				this.cand.clear();
				pathFound = true;
				return;
			}
			for (V w : graph.getSuccessorVertexSet(v)) {
				double newDist = this.dist.get(v) + graph.getWeight(v, w);
				if (this.dist.get(w) == Double.POSITIVE_INFINITY || newDist < this.dist.get(w)) {
					this.pred.put(w, v);
					this.dist.put(w, newDist);
					if (this.heu != null) {
						this.cand.add(w, newDist + this.heu.estimatedCost(w, g));
					} else {
						this.cand.add(w, newDist);
					}
				}
			}
		}
		pathFound = false;
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		// ...
		List<V> path = new LinkedList<>();
		if (pathFound) {
			V k = this.ziel;
			path.add(k);
			while (!k.equals(this.start)) {
				path.add(this.pred.get(k));
				k = this.pred.get(k);
			}
			List<V> revPath = new LinkedList<>();
			for (int i = path.size() - 1; i >= 0; i--) {
				revPath.add(path.get(i));
			}
			return revPath;
		}
		throw new IllegalArgumentException("Kein kürzester Weg berechnet.");
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		// ...
		if (pathFound)
			return this.dist.get(this.ziel);
		throw new IllegalArgumentException("Kein kürzester Weg berechnet.");
	}

}
