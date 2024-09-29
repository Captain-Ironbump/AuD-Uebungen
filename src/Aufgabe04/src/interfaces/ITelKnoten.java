package Aufgabe04.src.interfaces;

/**
 * Klasse für einen Telefonknoten. Ein Telefonknoten besteht aus einer ({@code x},{@code y})-Koordinate.
 */
public interface ITelKnoten {
    /**
     * Returns the value of the {@code x} record component.
     * @return the value of the {@code x} record component
     */
    public int x();
    
    /**
     * Returns the value of the {@code y} record component.
     * @return the value of the {@code y} record component
     */
    public int y();
}
