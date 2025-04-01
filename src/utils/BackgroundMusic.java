package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.config.ConfigArguments;

import java.io.File;

public class BackgroundMusic {
    private static BackgroundMusic instance;
    private MediaPlayer mediaPlayer;

    private BackgroundMusic() {
        String path = new File("src/sounds/backgroundMusic.mp3").toURI().toString();
        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(ConfigArguments.getVolume());
    }

    public static BackgroundMusic getInstance() {
        if (instance == null) {
            instance = new BackgroundMusic();
        }
        return instance;
    }

    public void play() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }


    public double getVolume() {
        return mediaPlayer.getVolume();
    }
}
