package org.example.fx_forint.models;

public class Deviza {
    private String devizanem;
    private Double arfolyam;

    public Deviza(String devizanem) {
        this.devizanem = devizanem;
    }

    public Deviza(String devizanem,Double arfolyam) {
        this.devizanem = devizanem;
        this.arfolyam = arfolyam;
    }
    public double getArfolyam() {
        return arfolyam;
    }
    public void setArfolyam(Double arfolyam) {
        this.arfolyam = arfolyam;
    }
    public String getDevizanem() {
        return devizanem;
    }

    public void setDevizanem(String devizanem) {
        this.devizanem = devizanem;
    }
}
