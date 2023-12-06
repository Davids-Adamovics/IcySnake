package lv.venta;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.Random;

public class backgroundMusic {

    private static MediaPlayer mediaPlayer;
    private String[] musicFilePaths;
    private static double volume = 0.3; // Default volume

    public backgroundMusic(String[] strings) {
    }

    public void BackgroundMusic(String[] musicFilePathsInput) {
        musicFilePaths = musicFilePathsInput;
        playRandomMusic();
    }

    private void playRandomMusic() {
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
    }

    public static void playPickupSound() {
        // Pickup sound
        Media sound = new Media(backgroundMusic.class.getResource("pickupsound.wav").toString());
        MediaPlayer pickupSoundPlayer = new MediaPlayer(sound);
        pickupSoundPlayer.setVolume(volume * 0.5);
        pickupSoundPlayer.play();
    }

    public static void playGameOverSound() {
        Media sound = new Media(backgroundMusic.class.getResource("gameOver.wav").toString());
        MediaPlayer gameOverSoundPlayer = new MediaPlayer(sound);
        gameOverSoundPlayer.setVolume(volume * 0.5);
        gameOverSoundPlayer.setCycleCount(1);
        gameOverSoundPlayer.play();
        //gameOverSoundPlayer.stop();
        
    }

    private String getRandomMusicFile() {
        Random random = new Random();
        int randomIndex = random.nextInt(musicFilePaths.length);
        return musicFilePaths[randomIndex];
    }
}
