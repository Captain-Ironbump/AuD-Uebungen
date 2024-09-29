package Aufgabe04.src;

import Aufgabe04.src.interfaces.ITelVerbindung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import Aufgabe04.src.interfaces.ITelKnoten;
import Aufgabe04.src.interfaces.ITelNet;

public class TelNet implements ITelNet {

    private PriorityQueue<ITelVerbindung> edges;
    private int lbg;
    private Map<ITelKnoten, Integer> telNodes;
    private int nodeNumber;
    private UnionFind<ITelKnoten> forest;
    private List<ITelVerbindung> minSpanTree;

    public TelNet(int lbg) {
        this.lbg = lbg;
        this.nodeNumber = 0;
        this.telNodes = new HashMap<>();
    }

    private int dist(int x1, int x2, int y1, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public boolean addTelKnoten(int x, int y) {
        ITelKnoten telNode = new TelKnoten(x, y);
        if (this.telNodes.containsKey(telNode)) {
            return false;
        }
        this.telNodes.put(telNode, nodeNumber++);
        return true;
    }

    @Override
    public boolean computeOptTelNet() {
        ITelKnoten nodes[] = this.telNodes.keySet().toArray(new ITelKnoten[this.nodeNumber]);
        this.forest = new UnionFind<>(nodes);
        this.edges = new PriorityQueue<>((o1, o2) -> {
            if (o1.c() < o2.c()) {
                return -1;
            } else if (o1.c() > o2.c()) {
                return 1;
            } else {
                return 0;
            }
        });
        this.minSpanTree = new ArrayList<>();
       
        for (int i = 0; i < this.nodeNumber - 1; i++) {
            ITelKnoten t1 = nodes[i];
            for (int j = i + 1; j < this.nodeNumber; j++) {
                ITelKnoten t2 = nodes[j];
                if (t1 != t2) {
                    int c = dist(t1.x(), t2.x(), t1.y(), t2.y());
                    if (c > this.lbg) {
                        c = Integer.MAX_VALUE;
                    }
                    this.edges.add(new TelVerbindung(t1, t2, c));
                }
            }
        }
        
        while (this.forest.size() != 1 && !this.edges.isEmpty()) {
            ITelVerbindung tmp = this.edges.poll();
            ITelKnoten t1 = this.forest.find(tmp.u());
            ITelKnoten t2 = this.forest.find(tmp.v());
            if (t1 != t2) {
                this.forest.union(t1, t2);
                this.minSpanTree.add(tmp);
            }
        }
        
        if (this.forest.size() == 1) { // Forlesung steht hier != 1, vielleicht ein Fehler 
            return false;
        }
        return true;
    }

    @Override
    public List<ITelVerbindung> getOptTelNet() throws IllegalStateException {
       if (this.minSpanTree == null || this.minSpanTree.isEmpty()) {
           throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        return this.minSpanTree;
    }

    @Override
    public int getOptTelNetKosten() throws IllegalStateException {
        if (this.minSpanTree == null || this.minSpanTree.isEmpty()) {
            throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        return this.minSpanTree.stream().mapToInt(ITelVerbindung::c).sum();
    }

    @Override
    public void drawOptTelNet(int xMax, int yMax, boolean directDraw) throws IllegalStateException {
        if (this.minSpanTree == null || this.minSpanTree.isEmpty()) {
            throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (ITelKnoten t : this.telNodes.keySet()) {
            StdDraw.filledSquare(t.x(), t.y(), 0.5);
        }
        StdDraw.setPenColor(StdDraw.RED);
        for (ITelVerbindung t : this.minSpanTree) {
            if (directDraw)
                StdDraw.line(t.u().x(), t.u().y(), t.v().x(), t.v().y());
            else {
                StdDraw.line(t.u().x(), t.u().y(), t.v().x(), t.u().y());
                StdDraw.line(t.v().x(), t.u().y(), t.v().x(), t.v().y());
            }
        }
    }

    @Override
    public void generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * (xMax + 1));
            int y = (int) (Math.random() * (yMax + 1));
            this.addTelKnoten(x, y);
        }
    }

    @Override
    public int size() {
        return this.nodeNumber;
    }

    public static void main(String[] args) {
        TelNet net = new TelNet(4);
        net.addTelKnoten(1, 1);
        net.addTelKnoten(3, 1);
        net.addTelKnoten(4, 2);
        net.addTelKnoten(3, 4);
        net.addTelKnoten(2, 6);
        net.addTelKnoten(4, 7);
        net.addTelKnoten(7, 6);
        net.computeOptTelNet();
        System.out.println("Kosten: " + net.getOptTelNetKosten());
        net.drawOptTelNet(10, 10, false);

        
        // TelNet netRand = new TelNet(100);
        // netRand.generateRandomTelNet(1000, 1000, 1000);
        // netRand.computeOptTelNet();
        // System.out.println("Kosten: " + netRand.getOptTelNetKosten());
        // netRand.drawOptTelNet(1000, 1000, false);
    }

}
