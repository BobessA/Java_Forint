package org.example.fx_forint.models;

public class Akod {

    private int ermeid;
    private int femid;
    private Anyag anyag;

    public Akod() {
    }

    public Akod(int femid) {
        this.femid = femid;
    }

    public int getErmeid() {
        return ermeid;
    }

    public void setErmeid(int ermeid) {
        this.ermeid = ermeid;
    }

    public int getFemid() {
        return femid;
    }

    public void setFemid(int femid) {
        this.femid = femid;
    }

    public Anyag getAnyag() {
        return anyag;
    }

    public void setAnyag(Anyag anyag) {
        this.anyag = anyag;
    }

}
