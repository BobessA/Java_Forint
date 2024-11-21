package org.example.fx_forint.models.Forex;

public class TradeData {

    private String id;
    private String instrument;
    private String openTime;
    private String units;
    private String price;
    private String pl;

    public TradeData(String id, String instrument, String openTime, String units, String price, String pl) {
        this.id = id;
        this.instrument = instrument;
        this.openTime = openTime;
        this.units = units;
        this.price = price;
        this.pl = pl;
    }

    public String getId() {
        return id;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getUnits() {
        return units;
    }

    public String getPrice() {
        return price;
    }

    public String getPl() {
        return pl;
    }
}
