package lv.venta;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class backgroundMusic {

	private static MediaPlayer mediaPlayer;
	private static MediaPlayer menuSong;
	private String[] musicFilePaths;
	static double volume = 1;

	public backgroundMusic(String[] strings) {
		musicFilePaths = strings;
		playRandomMusic();
	}

	public static void updateVolume() {
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volume);
		}
	}

	public void BackgroundMusic(String[] musicFilePathsInput) {
		musicFilePaths = musicFilePathsInput;
		playRandomMusic();
	}

	private void playRandomMusic() {

		stopMusic();

		String randomMusicFile = getRandomMusicFile();
		Media sound = new Media(getClass().getResource(randomMusicFile).toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(volume);
		mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
		mediaPlayer.play();

	}

	public static void stopMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		if (menuSong != null) {
			menuSong.stop();
		}
	}

	// menu music
	public static void playMenuMusic() {
		Media sound = new Media(backgroundMusic.class.getResource("game3.wav").toString());
		menuSong = new MediaPlayer(sound);
		menuSong.setVolume(volume * 0.2);
		menuSong.play();

	}

	// button sound
	public static void PlayButtonSound() {
		Media sound = new Media(backgroundMusic.class.getResource("buttonSound.wav").toString());
		MediaPlayer buttonSoundPlayed = new MediaPlayer(sound);
		buttonSoundPlayed.setVolume(volume * 0.5);
		buttonSoundPlayed.setCycleCount(1);
		buttonSoundPlayed.play();
	}

	// Pickup sound for all fruits
	public static void playPickupSound() {
		Media sound = new Media(backgroundMusic.class.getResource("pickupsound.wav").toString());
		MediaPlayer pickupSoundPlayer = new MediaPlayer(sound);
		pickupSoundPlayer.setVolume(volume * 0.5);
		pickupSoundPlayer.play();
	}

	// Star powerUp sound
	public static void playStarSound() {
		Media sound = new Media(backgroundMusic.class.getResource("starSound2.wav").toString());
		MediaPlayer starSoundPlayer = new MediaPlayer(sound);
		starSoundPlayer.setVolume(volume * 0.5);
		starSoundPlayer.play();
	}

	// Bomb powerUp sound
	public static void playBombSound() {
		Media sound = new Media(backgroundMusic.class.getResource("bombSound.wav").toString());
		MediaPlayer bombSoundPlayer = new MediaPlayer(sound);
		bombSoundPlayer.setVolume(volume * 0.5);
		bombSoundPlayer.play();
	}

	// coin powerUp sound
	public static void playCoinSound() {
		Media sound = new Media(backgroundMusic.class.getResource("coinSound.wav").toString());
		MediaPlayer coinSoundPlayer = new MediaPlayer(sound);
		coinSoundPlayer.setVolume(volume * 0.5);
		coinSoundPlayer.play();
	}
	// barrier hit sound

	public static void playBarrierSound() {
		Media sound = new Media(backgroundMusic.class.getResource("barrierSound.wav").toString());
		MediaPlayer barrierSoundPlayer = new MediaPlayer(sound);
		barrierSoundPlayer.setVolume(volume * 0.8);
		barrierSoundPlayer.play();
	}

	// game over sound
	public static void playGameOverSound() {
		stopMusic();
		Media sound = new Media(backgroundMusic.class.getResource("gameOver.wav").toString());
		MediaPlayer gameOverSoundPlayer = new MediaPlayer(sound);
		gameOverSoundPlayer.setVolume(volume * 0.5);
		gameOverSoundPlayer.setCycleCount(1);
		gameOverSoundPlayer.play();
	}

	private String getRandomMusicFile() {

		return musicFilePaths[0];
	}
}
