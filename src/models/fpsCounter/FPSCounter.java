package models.fpsCounter;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class FPSCounter {

    private long lastSecond = 0;
    private int frameCount = 0;

    public Label createFPSCounterLabel() {
        Label fpsLabel = new Label();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frameCount++;

                if (now - lastSecond >= 1_000_000_000) {
                    int currentFPS = frameCount;
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
