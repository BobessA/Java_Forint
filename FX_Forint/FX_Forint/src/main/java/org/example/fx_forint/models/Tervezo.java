package org.example.fx_forint.models;

public class Tervezo {

    public int tid;
    public String nev;

    public Tervezo() {
    }

    public Tervezo(int tid, String nev) {
        this.tid = tid;
        this.nev = nev;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    // toString metódus felülírása
    @Override
    public String toString() {
        return nev; // A nevét fogja visszaadni, így az lesz a megjelenített szöveg
    }
}
