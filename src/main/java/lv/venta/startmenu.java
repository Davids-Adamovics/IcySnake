package lv.venta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class startmenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        

        try {
            Scene myScene = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
            primaryStage.setTitle("IcySnake");
            primaryStage.setScene(myScene);
           
            primaryStage.show();
        } catch (IOException e) {
            // Handle FXML loading exception
            e.printStackTrace();
        }
    }

    private Image loadImage(String resourceName) {
        try (InputStream stream = getClass().getResourceAsStream(resourceName)) {
            if (stream != null) {
                return new Image(stream);
            } else {
                System.err.println("Image resource not found: " + resourceName);
            }
        } catch (IOException e) {
            // Handle IO exception
            e.printStackTrace();
        }
        return null;
    }
}
