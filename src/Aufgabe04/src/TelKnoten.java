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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TelKnoten other = (TelKnoten) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
