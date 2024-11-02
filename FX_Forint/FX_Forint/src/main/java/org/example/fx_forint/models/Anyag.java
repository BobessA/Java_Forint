package org.example.fx_forint.models;

public class Anyag {

    public int femid;
    public String femnev;

    public Anyag() {
    }

    public Anyag(int femid, String femnev) {
        this.femid = femid;
        this.femnev = femnev;
    }

    public int getFemid() {
        return femid;
    }

    public void setFemid(int femid) {
        this.femid = femid;
    }

    public String getFemnev() {
        return femnev;
    }

    public void setFemnev(String femnev) {
        this.femnev = femnev;
    }
}