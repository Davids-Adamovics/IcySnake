package lv.venta;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class register extends Application {

    // Arraylist allplayers
    public static ArrayList<player> allPlayers = new ArrayList<player>();

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	Scene myScene = FXMLLoader.load(getClass().getResource("primary.fxml"));
        primaryStage.setTitle("IcySnake");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }

    // Method to register a new player
    public static void registerPlayer(String username, byte age, enumGender gender) {
        player newPlayer = new player(username, age, gender);
        allPlayers.add(newPlayer);
    }

}
