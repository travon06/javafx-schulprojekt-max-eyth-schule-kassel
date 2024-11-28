package models.fpsCounter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FPSCounter {

    private static long lastFrameTime = 0;
    private static int frameCount = 0;
    private static int currentFPS = 0;

    public static Label createFPSCounter() {
        Label fpsLabel = new Label();
        AnimationTimer timer = new AnimationTimer() {
            private long lastSecond = 0;

            @Override
            public void handle(long now) {
                if(lastFrameTime == 0) {
                    lastFrameTime = now;
                    return;
                }

                frameCount++;

                if(now -lastSecond >= 1_000_000_000) {
                    currentFPS = frameCount;
                    frameCount = 0;
                    lastSecond = now;

                    fpsLabel.setText(String.format("FPS: %d", currentFPS));
                }
            }
        };

        timer.start();
        return fpsLabel;
    }
}
