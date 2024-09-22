// O. Bittel;
// 2.8.2023

package Aufgabe02.aufgabe2.aufgabe2.graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;


/**
 * Klasse zur Analyse von Web-Sites.
 *
 * @author Oliver Bittel
 * @since 30.10.2023
 */
public class AnalyzeWebSite {
    private static final String packagePath = "src/Aufgabe02/aufgabe2/aufgabe2/";
    private static final String dirName = packagePath + "data/WebSiteKlein";
    private static final String dirNameBig = packagePath + "data/WebSiteGross";
    public static void main(String[] args) throws IOException {
        // check that both directories exist
        File webSite = new File(dirName);
        // for debugging
        //System.out.println(webSite.getAbsolutePath());
        if (!webSite.exists() || !webSite.isDirectory()) {
            System.out.println("Directory " + dirName + " does not exist.");
            return;
        }
        File webSiteBig = new File(dirNameBig);
        if (!webSiteBig.exists() || !webSiteBig.isDirectory()) {
            System.out.println("Directory " + dirNameBig + " does not exist.");
            return;
        }
        // Graph aus Website erstellen und ausgeben:
        //DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("data/WebSiteKlein");
        System.out.println("-----------WebSiteKlein-----------");
        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite(dirName);
        System.out.println("Anzahl Seiten: \t" + webSiteGraph.getNumberOfVertexes());
        System.out.println("Anzahl Links: \t" + webSiteGraph.getNumberOfEdges());
        //System.out.println(webSiteGraph);

        // Starke Zusammenhangskomponenten berechnen und ausgeben
        StrongComponents<String> sc = new StrongComponents<>(webSiteGraph);
        System.out.println(sc.numberOfComp());
        //System.out.println(sc);

        // Page Rank ermitteln und Top-100 ausgeben
        pageRank(webSiteGraph, true);

        System.out.println("-----------WebSiteGroess-----------");
        DirectedGraph<String> webSiteBigGraph = buildGraphFromWebSite(dirNameBig);
        System.out.println("Anzahl Seiten: \t" + webSiteBigGraph.getNumberOfVertexes());
        System.out.println("Anzahl Links: \t" + webSiteBigGraph.getNumberOfEdges());
        StrongComponents<String> scBig = new StrongComponents<>(webSiteBigGraph);
        System.out.println(scBig.numberOfComp());
        pageRank(webSiteBigGraph, false);
    }

    /**
     * Liest aus dem Verzeichnis dirName alle Web-Seiten und
     * baut aus den Links einen gerichteten Graphen.
     *
     * @param dirName Name eines Verzeichnis
     * @return gerichteter Graph mit Namen der Web-Seiten als Knoten und Links als gerichtete Kanten.
     */
    private static DirectedGraph<String> buildGraphFromWebSite(String dirName) throws IOException {
        File webSite = new File(dirName);
        DirectedGraph<String> webSiteGraph = new AdjacencyListDirectedGraph<String>();

        for (File f : webSite.listFiles()) {
            String from = f.getName();
            LineNumberReader in = new LineNumberReader(new FileReader(f));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("href")) {
                    String[] s_arr = line.split("\"");
                    String to = s_arr[1];
                    webSiteGraph.addEdge(from, to);
                }
            }
            in.close();
        }
        return webSiteGraph;
    }

    /**
     * pageRank ermittelt Gewichte (Ranks) von Web-Seiten
     * aufgrund ihrer Link-Struktur und gibt sie aus.
     *
     * @param g gerichteter Graph mit Web-Seiten als Knoten und Links als Kanten.
     */
    private static <V> void pageRank(DirectedGraph<V> g, boolean isSmall) {
        int nI = 10;
        double alpha = 0.5;

        // Definiere und initialisiere rankTable:
        // Ihr Code: ...
        Map<V, Double> rankTable = new HashMap<>();
        for (V v : g.getVertexSet()) {
            rankTable.put(v, 1.0);
        }

        // Iteration:
        // Ihr Code: ...
        for (int i = 0; i < nI; i++) {
            Map<V, Double> newRankTable = new HashMap<>();
            for (V website : g.getVertexSet()) {
                double rank = 0;
                for (V v : g.getSuccessorVertexSet(website)) {
                    rank += rankTable.get(v) / g.getPredecessorVertexSet(v).size();
                }
                newRankTable.put(website, (1 - alpha) + alpha * rank);
            }
            rankTable = newRankTable;
        }

        // Rank Table ausgeben (nur für data/WebSiteKlein):
        // Ihr Code: ...
        
        if (isSmall) {
            for (V v : g.getVertexSet()) {
                System.out.println(v + " : " + rankTable.get(v));
            }
        }
        

        // Nach Ranks sortieren Top 100 ausgeben (nur für data/WebSiteGross):
        // und Top-Seite mit ihren Vorgängern und Ranks ausgeben (nur für data/WebSiteGross):
        // Ihr Code: ...
        if (!isSmall) {
            List<Map.Entry<V, Double>> list = new ArrayList<>(rankTable.entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (int i = 0; i < 100; i++) {
                System.out.println(list.get(i).getKey() + " : " + list.get(i).getValue());
            }
            System.out.println("Top-Site with predecessors:");
            V top = list.get(0).getKey();
            Set<V> predecessors = g.getPredecessorVertexSet(top);
            System.out.println("best Site: " + top + " : " + list.get(0).getValue());
            for (V v : predecessors) {
                System.out.println("pre: " + v + " : " + rankTable.get(v));
            }
        }
    }
}

