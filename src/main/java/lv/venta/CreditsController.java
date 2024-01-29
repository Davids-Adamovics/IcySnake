package lv.venta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreditsController {

    @FXML // definē FXML objektu
    private Button backButton;

    @FXML   // credits lapai piešķir BACK pogu, kas aizver esošo scene
    public void goBack(ActionEvent event) {
    	backgroundMusic.PlayButtonSound();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // pogai uzliek stage un Node objektu, ar kura apalīdzību atrod
        currentStage.close();                                                           // kur programmā atrodas šī poga un tad to aizver.
    }

    public void start(Stage primaryStage) {
    }
}
