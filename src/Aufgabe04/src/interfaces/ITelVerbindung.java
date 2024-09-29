package Aufgabe04.src.interfaces;

/**
 * Klasse für eine Telefonverbindung. Eine Telefonverbindung besteht aus Anfangsknoten {@code n}, Endknoten {@code v} und Verbindungsknoten {@code c}.
 */
public interface ITelVerbindung {

    /**
     * Returns the value of the {@code u} record component.
     * @return the value of the {@code u} record component
     */
    public ITelKnoten u();
    
    /**
     * Returns the value of the {@code v} record component.
     * @return the value of the {@code v} record component
     */
    public ITelKnoten v();

    /**
     * Returns the value of the {@code c} record component.
     * @return the value of the {@code c} record component
     */
    public int c();
}
