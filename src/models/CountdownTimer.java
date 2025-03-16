package models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.config.ConfigArguments;

public class CountdownTimer {
    private int seconds;
    private Label timerLabel;
    private Timeline timeline;

    public CountdownTimer(Label timerLabel) {
        this.seconds = 10;
        this.timerLabel = timerLabel;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds--;
            timerLabel.setText(String.valueOf(seconds));
            System.out.println(seconds);
            if (seconds <= 0) {
                timeline.stop();
                timerLabel.setText("Zeit abgelaufen!");
            }
        }));
        
    }

    public void start() {
        this.timeline.setCycleCount(seconds);
        this.timeline.play();
    }
}
