module org.example.fx_forint {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.fx_forint to javafx.fxml;
    exports org.example.fx_forint;
}