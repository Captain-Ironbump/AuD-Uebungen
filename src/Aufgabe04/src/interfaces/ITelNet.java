package Aufgabe04.src.interfaces;

import java.util.List;

/**
 * Klasse zur Verwaltung von Telefonknoten mit ({@code x} ,{@code y})-Koordinaten und zur Berechnung eines
 * minimal aufspannenden Baums mit dem Algorithmus von Kruksal.
 * Kantengewichte sind durch den Manhatten-Abstand definiert.
 */
public interface ITelNet {
    /**
     * Fügt einen neuen Telefonknoten mit Koordinaten ({@code x} ,{@code y}) dazu.
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @return true, falls die Koordinate neu ist, sonst false
     */
    public boolean addTelKnoten(int x, int y);

    /**
     * Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem
     * Algorithmus von Kruksal.
     * @return true, falls es einen minimal aufspannenden Baum gibt, sonst false
     */
    public boolean computeOptTelNet();

    /**
     * Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
     * @return Liste von Telefonverbindungen.
     * @throws IllegalStateException - falls nicht zuvor {@link #computeOptTelNet() computeOptTelNet() } erfolgreich durchgeführt wurde.
     */
    public List<ITelVerbindung> getOptTelNet() throws IllegalStateException;

    /**
     * Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
     * @return Gesamtkosten eines optimalen Telefonnetzes.
     * @throws IllegalStateException - falls nicht zuvor {@link #computeOptTelNet() computeOptTelNet() } erfolgreich durchgeführt wurde.
     */
    public int getOptTelNetKosten() throws IllegalStateException;

    /**
     * Zeichnet das gefundene optimale Telefonnetz mit der Größe {@code xMax} * {@code yMax} in ein Fenster.
     * @param xMax - Maximale x-Größe.
     * @param yMax - Maximale y-Größe.
     * @throws IllegalStateException- falls nicht zuvor {@link #computeOptTelNet() computeOptTelNet() } erfolgreich durchgeführt wurde.
     */
    public void drawOptTelNet(int xMax, int yMax) throws IllegalStateException; 
    
    /**
     * Fügt {@code n} zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,{@code xMax}] und y-Koordinate aus [0,{@code yMax}].  
     * @param n - Anzahl Telefonknoten
     * @param xMax - Intervallgrenz für x-Koordinate.
     * @param yMax - Intervallgrenz für y-Koordinate.
     */
    public void generateRandomTelNet(int n, int xMax, int yMax);

    /**
     * Liefert die Anzahl der Knoten des Telefonnetzes zurück.
     * @return Anzahl der Knoten des Telefonnetzes.
     */
    public int size();
}
