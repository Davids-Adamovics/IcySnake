package lv.venta;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreditsController {

    @FXML
    private Button backButton;

    @FXML
    public void goBack(ActionEvent event) {
    	backgroundMusic.PlayButtonSound();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void start(Stage primaryStage) {
    }
}
