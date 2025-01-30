import java.util.ArrayList;

import graphics.Graphic;
import graphics.GraphicReader;
import graphics.Graphics;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import language.TextReader;
import levels.Level;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;
import utils.mapConfig.MapReader;

public class App extends Application {

    private Level currentLevel;
    private Level nextLevel;
    private Level temp;

    static {
        // load configurations
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
        GraphicReader.readGraphics();
        TextReader.readTexts();
    }

    @Override
    public void start(Stage primaryStage) {
        ArrayList<Level> levels = new ArrayList<>();
        for(int i = 0; i < MapReader.MAPS.size(); i++) {
            String mapName = MapReader.MAPS.get(i).split(":")[1].split("&")[0];
            levels.add(new Level(new Stage(), mapName, levels)); 
        }

        currentLevel = levels.get(0);
        currentLevel.start();
    
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(currentLevel.getFinished()) {
                    nextLevel = levels.get(levels.indexOf(currentLevel) + 1);
                    nextLevel.start();
                    temp = currentLevel;
                    temp.stop();
                    currentLevel = nextLevel;
                }
            }
        };

        timer.start();
        

    }

    public static void main(String[] args) {
        launch(args);
    }
}
