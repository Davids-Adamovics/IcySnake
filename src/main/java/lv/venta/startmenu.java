package lv.venta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class startmenu extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	Scene myScene = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
        primaryStage.setTitle("IcySnake");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }

}
