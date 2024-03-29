package lv.venta;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lv.venta.Game;
import lv.venta.backgroundMusic;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

import lv.venta.enumGender;
import lv.venta.player;

public class PrimaryController {

	@FXML
	private TextField usernameInput;
	@FXML
	private TextField ageInput;
	@FXML
	private RadioButton malePick;
	@FXML
	private RadioButton femalePick;
	@FXML
	private Button buttonSave;
	@FXML
	private Button buttonGuest;
	private backgroundMusic musicPlayer;

	@FXML
	private Slider volumeSlider;

	public static player currentPlayer;

	public void SwitchTogame(ActionEvent event) throws IOException {		// nospiežot register 
		try {
			backgroundMusic.stopMusic();	// apstājas mūzika

			String username = usernameInput.getText();				// saglabā player datus
			byte age = Byte.parseByte(ageInput.getText());
			enumGender gender = malePick.isSelected() ? enumGender.male : enumGender.female;

			currentPlayer = new player(username, age, gender);

			System.out.println("Player Information: \n" + currentPlayer);		// izvada player datus konsolē

			backgroundMusic.PlayButtonSound();	// noskan pogas skaņa

			Game game = new Game();			// palaiž spēli
			Stage gameStage = new Stage();
			game.start(gameStage);

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();		// aizver esošo scene
			currentStage.close();

		} catch (Exception e) {			// noķer ķļudas
			e.printStackTrace();
		}
	}

	public void SwitchTogameGuest(ActionEvent event) throws IOException {		// Līdzīgi kā register, bet bez player saglabāšanas

		backgroundMusic.stopMusic();

		currentPlayer = new player();

		System.out.println("Player Information: \n" + currentPlayer);

		backgroundMusic.PlayButtonSound();

		Game game = new Game();
		Stage gameStage = new Stage();
		game.start(gameStage);

		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		currentStage.close();

	}


	public player getCurrentPlayer() {
		return currentPlayer;

	}

}
