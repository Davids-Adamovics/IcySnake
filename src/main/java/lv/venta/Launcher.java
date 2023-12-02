package lv.venta;

//importi
import javafx.application.Application;
import javafx.stage.Stage;
import lv.venta.register;

//extends application, lai varetu izmantot javafx
public class Launcher extends Application {

    // launch main
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // izveido jaunu register klasi
            register register1 = new register();
            // palaiz izveidoto klasi
            register1.start(primaryStage);

            // prieks debug, ja nu gadijuma ir kluda, tad console to pazinos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
