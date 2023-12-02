module lv.venta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens lv.venta to javafx.fxml;
    exports lv.venta;

}
