package lv.venta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class credits extends Application {

    public void start(Stage primaryStage) throws Exception {
        Image iconImage = new Image(getClass().getResource("logologo.png").toExternalForm()); // uzliek window ikonu logologo.png
    	Scene myScene = FXMLLoader.load(getClass().getResource("credits.fxml")); // palaiž credits.fxml
        primaryStage.setTitle("IcySnake");
        primaryStage.setScene(myScene);
        primaryStage.getIcons().add(iconImage);
        primaryStage.show();

    }


}
