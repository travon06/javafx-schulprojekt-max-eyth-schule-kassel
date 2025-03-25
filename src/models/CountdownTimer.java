package models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import language.Texts;
import utils.config.ConfigArguments;

public class CountdownTimer {
    private int seconds;
    private Label timerLabel;
    private Timeline timeline;

    public CountdownTimer(Label timerLabel, int seconds) {
        this.seconds = seconds;
        this.timerLabel = timerLabel;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.seconds--;
            timerLabel.setText(String.format("%s : %d", Texts.getTextByName("timer").getTextInLanguage(), this.seconds));
            if (this.seconds <= 0) {
                timeline.stop();
            }
        }));
        
    }

    public boolean isDone() {
        return this.seconds <= 0;
    } 

    public void start() {
        this.timeline.setCycleCount(seconds);
        this.timeline.play();
    }
}
