import graphics.GraphicReader;
import javafx.application.Application;
import javafx.stage.Stage;
import language.TextReader;
import models.Screens.EndScreen;
import models.Screens.LevelSelection;
import models.Screens.MapMaker;
import models.Screens.StartScreen;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;
public class App extends Application {
    static {
        // load configurations
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
        GraphicReader.readGraphics();
        TextReader.readTexts();
    }

    @Override
    public void start(Stage primaryStage) {
        if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("DEVELOPMENT_MODE"))) {
            new LevelSelection(primaryStage);
        } else {
            new StartScreen(primaryStage);
        }


        new MapMaker(primaryStage);
        // new EndScreen(primaryStage);
        // new StartScreen(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
