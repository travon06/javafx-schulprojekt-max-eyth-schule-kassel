import graphics.GraphicReader;
import javafx.application.Application;
import javafx.stage.Stage;
import language.TextReader;
import models.Screens.StartScreen;
import models.Screens.MapMaker.MapModeSelection;
import utils.BackgroundMusic;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;
import utils.statistics.StatisticReader;
import utils.statistics.StatisticWriter;
import utils.statistics.Statistics;
public class App extends Application {
    static {
        // load configurations
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
        GraphicReader.readGraphics();
        TextReader.readTexts();
        StatisticReader.readStatistics();

        if(Boolean.parseBoolean(Statistics.getStatisticValue("FIRST_TIME_IN_GAME"))) {
            StatisticWriter.resetStatistics();
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        Statistics.setStatisticValue("FIRST_TIME_IN_GAME", "FALSE");

        int penis = 0
        ;  
        // 0 = Game
        // 1 = MapWriter
        // 2 = MapMaker

        if(penis == 0) {
            BackgroundMusic.getInstance().play();
            new StartScreen(primaryStage);
        } else if (penis == 2) {
            new MapModeSelection(primaryStage);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
