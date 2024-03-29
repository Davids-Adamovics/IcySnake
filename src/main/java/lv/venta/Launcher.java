package lv.venta;

//importi
import javafx.application.Application;
import javafx.stage.Stage;

//extends application, lai varetu izmantot javafx
public class Launcher extends Application {

    // launch main
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
        	
        	backgroundMusic.playMenuMusic();
            // izveido jaunu register klasi
            startmenu startmenu1 = new startmenu();
            // palaiz izveidoto klasi
            startmenu1.start(primaryStage);

            // prieks debug, ja nu gadijuma ir kluda, tad console to pazinos
        } catch (Exception e) { // new update
            e.printStackTrace();
        }
    }
}
