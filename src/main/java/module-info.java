module lv.venta {
    requires javafx.controls;
    requires javafx.fxml;

    opens lv.venta to javafx.fxml;
    exports lv.venta;
}
