package lv.venta;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Random;

public class backgroundMusic {

    private MediaPlayer mediaPlayer;
    private String[] musicFilePaths;
    private double volume = 0.5; // skaļums

    public backgroundMusic(String[] musicFilePaths) {
        this.musicFilePaths = musicFilePaths;
        playRandomMusic();
    }

    private void playRandomMusic() {
        String randomMusicFile = getRandomMusicFile();
        Media sound = new Media(getClass().getResource(randomMusicFile).toString());
        mediaPlayer = new MediaPlayer(sound);
        



        //mediaPlayer.setVolume(volume);

        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }

    private String getRandomMusicFile() {
        Random random = new Random();
        int randomIndex = random.nextInt(musicFilePaths.length);
        return musicFilePaths[randomIndex];
    }

    // apstādina skaņu
    public void stopMusic() {
        mediaPlayer.stop();
    }

    // skaņas regulētājs
    /* 
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
    */
}
