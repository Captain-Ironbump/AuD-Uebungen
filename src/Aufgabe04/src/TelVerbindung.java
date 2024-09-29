package Aufgabe04.src;

import Aufgabe04.src.interfaces.ITelVerbindung;
import Aufgabe04.src.interfaces.ITelKnoten;

public record TelVerbindung(ITelKnoten u, ITelKnoten v, int c) implements ITelVerbindung {

    public TelVerbindung(ITelKnoten u, ITelKnoten v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    @Override
    public ITelKnoten u() {
        return this.u;
    }

    @Override
    public ITelKnoten v() {
        return this.v;
    }

    @Override
    public int c() {
        return this.c;
    }
}
