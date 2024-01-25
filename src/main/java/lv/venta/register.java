package lv.venta;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class register extends Application {

    // Arraylist allplayers
    public static ArrayList<player> allPlayers = new ArrayList<player>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
    	Scene myScene = FXMLLoader.load(getClass().getResource("primary.fxml"));
        primaryStage.setTitle("IcySnake");
        primaryStage.setScene(myScene);
        primaryStage.getIcons().add(iconImage);
        primaryStage.show();

    }

    // register a new player
    public static void registerPlayer(String username, byte age, enumGender gender) {
        player newPlayer = new player(username, age, gender);
        allPlayers.add(newPlayer);
    }

}
