package Aufgabe03.aufgabe3.aufgabe3.src.shortestPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Klasse für Scotland Yard Heuristik.
 * Bietet eine Methode estimatedCost an, die
 * die Distanz zweier Knoten im Scotland-Yard-Spielplan schätzt.
 * Die Heuristik wird für A* benötigt.
 *
 * @author Oliver Bittel
 * @since 30.06.2024
 */
public class ScotlandYardHeuristic implements Heuristic<Integer> {
    private Map<Integer, Point> coord; // Ordnet jedem Knoten seine Koordinaten zu
    private static final String DATA_PATH = "src/Aufgabe03/aufgabe3/aufgabe3/data/";
    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Der Konstruktor liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten
     * von der Datei ScotlandYard_Knoten.txt in eine Map ein.
     */
    public ScotlandYardHeuristic() throws FileNotFoundException {
        // Lese Koordinaten von ScotlandYard_Knoten.txt in eine Map.
        // ...
        try (Scanner scanner = new Scanner(new File(DATA_PATH + "ScotlandYard_Knoten.txt"))) {
            this.coord = new HashMap<>();
            while (scanner.hasNext()) {
                int node = scanner.nextInt();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                coord.put(node, new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // for (var entry : coord.entrySet()) {
        //     System.out.println(entry.getKey() + " -> " + "(" + entry.getValue().x + ", " + entry.getValue().y + ")");
        // }
    }

    /**
     * Liefert einen skalierten Euklidischen Abstand zwischen Knoten u und v zurück.
     * Da die Koordinaten von x und y in Pixeleinheiten sind, wird
     * der Euklidische Abstand mit einem Faktor zwischen 0.02 bis 0.1 skaliert.
     * @param u Knoten
     * @param v Knoten
     * @return skalierter Euklidischer Abstand als geschätze Kosten
     */
    public double estimatedCost(Integer u, Integer v) {
        // ...
        Point p1 = coord.get(u);
        Point p2 = coord.get(v);
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy) * 0.02;
    }
}
