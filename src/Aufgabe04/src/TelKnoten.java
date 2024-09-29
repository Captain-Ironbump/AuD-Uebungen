package Aufgabe04.src;

import Aufgabe04.src.interfaces.ITelKnoten;

public record TelKnoten(int x, int y) implements ITelKnoten {

    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int x() {
        return this.x;    
    }

    @Override
    public int y() {
        return this.y;
    }
}
