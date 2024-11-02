module org.example.fx_forint {
    requires java.naming;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens org.example.fx_forint to javafx.fxml;
    exports org.example.fx_forint;
    exports org.example.fx_forint.controller;
    opens org.example.fx_forint.models to org.hibernate.orm.core;
    opens org.example.fx_forint.controller to javafx.fxml;
}