package org.example.fx_forint.models;

import java.util.Date;

public class Erme {

    private int ermeid;
    private int cimlet;
    private double tomeg;
    private int darab;
    private Date kiadas;
    private Date bevonas;
    public Erme() {
    }

    public Erme(int cimlet, double tomeg, int darab, Date kiadas, Date bevonas) {
        this.cimlet = cimlet;
        this.tomeg = tomeg;
        this.darab = darab;
        this.kiadas = kiadas;
        this.bevonas = bevonas;
    }

    public int getErmeid() {
        return ermeid;
    }

    public void setErmeid(int ermeid) {
        this.ermeid = ermeid;
    }

    public int getCimlet() {
        return cimlet;
    }

    public void setCimlet(int cimlet) {
        this.cimlet = cimlet;
    }

    public double getTomeg() {
        return tomeg;
    }

    public void setTomeg(double tomeg) {
        this.tomeg = tomeg;
    }

    public int getDarab() {
        return darab;
    }

    public void setDarab(int darab) {
        this.darab = darab;
    }

    public Date getKiadas() {
        return kiadas;
    }

    public void setKiadas(Date kiadas) {
        this.kiadas = kiadas;
    }

    public Date getBevonas() {
        return bevonas;
    }

    public void setBevonas(Date bevonas) {
        this.bevonas = bevonas;
    }
}
