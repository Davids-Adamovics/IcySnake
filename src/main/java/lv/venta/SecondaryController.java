package lv.venta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SecondaryController {

    @FXML
    private Button startButton;

    @FXML
    private Button createButton;

    @FXML
    private Button exitButton;

    public void openRegister(ActionEvent event) throws IOException {
        try {
            // Create a new Register object
            register register1 = new register();
            // Start the created object
            Stage primaryStage = new Stage();
            register1.start(primaryStage);

            // For debugging purposes, print the stack trace in case of an exception
        } catch (Exception e) { // new update
            e.printStackTrace();
        }
    }

    public void openCredits(ActionEvent event) throws Exception {
        credits credits1 = new credits();

        Stage creditsStage = new Stage();
        credits1.start(creditsStage);


    }

    public void exitOut(ActionEvent event) {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
