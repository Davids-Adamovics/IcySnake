package lv.venta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;


public class SecondaryController {

    @FXML
    private Button startButton;

    @FXML
    private Button optionsButton;

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

    public void openOptions(ActionEvent event) {
        // Implement the logic to open the options screen here
        System.out.println("Options button clicked");
    }

    public void exitOut(ActionEvent event) {
        // Implement the logic to exit the application here
        System.out.println("Exit button clicked");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
