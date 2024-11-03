module org.example.fx_forint {
    //requires java.naming;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    exports org.example.fx_forint;
    exports org.example.fx_forint.controller;
    exports org.example.fx_forint.models;

    opens org.example.fx_forint to javafx.fxml;
    opens org.example.fx_forint.models to javafx.fxml;
    opens org.example.fx_forint.controller to javafx.fxml;
    opens org.example.fx_forint.helper to javafx.fxml;
}