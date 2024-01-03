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
    
    
    public static void playMenuMusic() {
        // menu music

        Media sound = new Media(backgroundMusic.class.getResource("game3.wav").toString());
        menuSong = new MediaPlayer(sound);
        menuSong.setVolume(volume * 0.2);
        menuSong.play();
        
    }
    
   
    public static void PlayButtonSound() {
    	Media sound = new Media(backgroundMusic.class.getResource("buttonSound.wav").toString());
        MediaPlayer buttonSoundPlayed = new MediaPlayer(sound);
        buttonSoundPlayed.setVolume(volume * 0.5);
        buttonSoundPlayed.setCycleCount(1);
        buttonSoundPlayed.play();
    }
    
    public static void playPickupSound() {
        // Pickup sound for all fruits
        Media sound = new Media(backgroundMusic.class.getResource("pickupsound.wav").toString());
        MediaPlayer pickupSoundPlayer = new MediaPlayer(sound);
        pickupSoundPlayer.setVolume(volume * 0.5);
        pickupSoundPlayer.play();
    }

    public static void playStarSound() {
        // Star powerUp sound
        Media sound = new Media(backgroundMusic.class.getResource("").toString());
        MediaPlayer starSoundPlayer = new MediaPlayer(sound);
        starSoundPlayer.setVolume(volume * 0.5);
        starSoundPlayer.play();
    }
    public static void playBombSound() {
        // Bomb powerUp sound
        Media sound = new Media(backgroundMusic.class.getResource("").toString());
        MediaPlayer bombSoundPlayer = new MediaPlayer(sound);
        bombSoundPlayer.setVolume(volume * 0.5);
        bombSoundPlayer.play();
    }
    public static void playCoinSound() {
        // coin powerUp sound
        Media sound = new Media(backgroundMusic.class.getResource("").toString());
        MediaPlayer coinSoundPlayer = new MediaPlayer(sound);
        coinSoundPlayer.setVolume(volume * 0.5);
        coinSoundPlayer.play();
    }
    public static void playBarrierSound() {
        // barrier hit sound
        Media sound = new Media(backgroundMusic.class.getResource("").toString());
        MediaPlayer barrierSoundPlayer = new MediaPlayer(sound);
        barrierSoundPlayer.setVolume(volume * 0.5);
        barrierSoundPlayer.play();
    }
    
    public static void playGameOverSound() {
    	stopMusic();

        Media sound = new Media(backgroundMusic.class.getResource("gameOver.wav").toString());
        MediaPlayer gameOverSoundPlayer = new MediaPlayer(sound);
        gameOverSoundPlayer.setVolume(volume * 0.5);
        gameOverSoundPlayer.setCycleCount(1);
        gameOverSoundPlayer.play();
    }

    private String getRandomMusicFile() {
        // Your logic to get a random music file path
        // For now, using the first path in the array
        return musicFilePaths[0];
    }
}
