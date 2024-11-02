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
}
