package org.example.fx_forint.models;

public class DevizaChartItem {
    private Deviza deviza;
    private String date;

    public DevizaChartItem(Deviza deviza, String date) {
        this.deviza = deviza;
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Deviza getDeviza() {
        return deviza;
    }
    public void setDeviza(Deviza deviza) {
        this.deviza = deviza;
    }
}
