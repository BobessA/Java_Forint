package org.example.fx_forint.models;

public class Tkod {

    private int ermeid;
    private int tervezoid;
    private Erme erme;
    private Tervezo tervezo;

    public Tkod() {
    }

    public Tkod(int ermeid, int tervezoid) {
        this.ermeid = ermeid;
        this.tervezoid = tervezoid;
    }

    public int getErmeid() {
        return ermeid;
    }

    public void setErmeid(int ermeid) {
        this.ermeid = ermeid;
    }

    public int getTervezoid() {
        return tervezoid;
    }

    public void setTervezoid(int tervezoid) {
        this.tervezoid = tervezoid;
    }

    public Erme getErme() {
        return erme;
    }

    public void setErme(Erme erme) {
        this.erme = erme;
    }

    public Tervezo getTervezo() {
        return tervezo;
    }

    public void setTervezo(Tervezo tervezo) {
        this.tervezo = tervezo;
    }
}
