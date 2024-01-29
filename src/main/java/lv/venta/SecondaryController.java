package lv.venta;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    
  

    public void openRegister(ActionEvent event) throws IOException {    // start poga
        try {
        	backgroundMusic.PlayButtonSound();
            register register1 = new register();
            Stage primaryStage = new Stage();
            register1.start(primaryStage);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    public void openCredits(ActionEvent event) throws Exception {   //credits poga
    	backgroundMusic.PlayButtonSound();
        credits credits1 = new credits();

        Stage creditsStage = new Stage();
        credits1.start(creditsStage);


    }
   


    public void exitOut(ActionEvent event) {    // exit poga
    	backgroundMusic.PlayButtonSound();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


 



}
