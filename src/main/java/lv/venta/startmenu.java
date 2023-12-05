package lv.venta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class startmenu extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
    	Scene myScene = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
        primaryStage.setTitle("IcySnake");
        primaryStage.setScene(myScene);
        primaryStage.getIcons().add(iconImage);
        primaryStage.show();

    }

}
