module org.example.fx_forint {
    //requires java.naming;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires jakarta.xml.ws;

    exports org.example.fx_forint;
    exports org.example.fx_forint.controller;
    exports org.example.fx_forint.models;

    opens org.example.fx_forint to javafx.fxml;
    opens org.example.fx_forint.models to javafx.fxml;
    opens org.example.fx_forint.controller to javafx.fxml;
    opens org.example.fx_forint.helper to javafx.fxml;

    opens soap to com.sun.xml.bind, com.sun.xml.ws;
    //opens org.example.fx_forint to javafx.fxml, com.sun.xml.bind;
}