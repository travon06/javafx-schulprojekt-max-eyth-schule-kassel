import java.util.ArrayList;

import graphics.GraphicReader;
import javafx.application.Application;
import javafx.stage.Stage;
import language.TextReader;
import levels.Level;
import models.Screens.LevelSelection;
import models.Screens.StartScreen;
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
            Level level = new Level(primaryStage, mapName, levels);
            level.addFPSCounter();
            levels.add(level); 
        } 

        StartScreen startScreen = new StartScreen(primaryStage, levels);

        

        // currentLevel = levels.get(0);
        // currentLevel.start();
    
        // AnimationTimer timer = new AnimationTimer() {
        //     @Override
        //     public void handle(long now) {
        //         if(currentLevel.getFinished()) {
        //             nextLevel = levels.get(levels.indexOf(currentLevel) + 1);
        //             nextLevel.start();
        //             temp = currentLevel;
        //             temp.stop();
        //             currentLevel = nextLevel;
        //         }
        //     }
        // };

        // timer.start();
        

    }

    public static void main(String[] args) {
        launch(args);
    }
}
