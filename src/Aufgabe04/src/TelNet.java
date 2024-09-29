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
        Integer[] collection = new Integer[nodeNumber];
        for (int i = 0; i < this.nodeNumber; i++) {
            collection[i] = i;
        }
        UnionFind<Integer> forest = new UnionFind<>(collection);
        this.edges = new PriorityQueue<>();

        ITelKnoten[] nodes = telNodes.keySet().toArray(new TelKnoten[this.nodeNumber]);
        
        this.minSpanTree = new ArrayList<>();
        while (this.forest.size() != 1 && !this.edges.isEmpty()) {
           ITelVerbindung tmp = this.edges.poll(); 
        }
        return false;
    }

    @Override
    public List<ITelVerbindung> getOptTelNet() throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOptTelNet'");
    }

    @Override
    public int getOptTelNetKosten() throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOptTelNetKosten'");
    }

    @Override
    public void drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawOptTelNet'");
    }

    @Override
    public void generateRandomTelNet(int n, int xMax, int yMax) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateRandomTelNet'");
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

}
