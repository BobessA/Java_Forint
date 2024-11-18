module org.example.fx_forint {
    //requires java.naming;
    requires javafx.fxml;
    requires javafx.controls;
    requires jakarta.xml.ws;
    requires com.google.gson;
    requires httpcore;
    requires httpclient;
    requires java.sql;

    exports org.example.fx_forint;
    exports org.example.fx_forint.controller;
    exports org.example.fx_forint.models;

    opens org.example.fx_forint to javafx.fxml;
    opens org.example.fx_forint.models to javafx.fxml;
    opens org.example.fx_forint.controller to javafx.fxml;
    opens org.example.fx_forint.helper to javafx.fxml;
    opens org.example.fx_forint.config to javafx.fxml;
    opens com.oanda.v20;
    opens com.oanda.v20.account;
    opens com.oanda.v20.pricing;
    opens com.oanda.v20.pricing_common;
    opens com.oanda.v20.order;
    opens com.oanda.v20.instrument;
    opens com.oanda.v20.transaction;
    opens com.oanda.v20.trade;
    exports com.oanda.v20.primitives;
    exports com.oanda.v20.transaction;
    opens soap to com.sun.xml.bind, com.sun.xml.ws;
    //opens org.example.fx_forint to javafx.fxml, com.sun.xml.bind;
}