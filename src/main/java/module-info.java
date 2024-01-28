module lv.venta {

    //modulis priekš vscode
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;

    opens lv.venta to javafx.fxml;
    exports lv.venta;

}
