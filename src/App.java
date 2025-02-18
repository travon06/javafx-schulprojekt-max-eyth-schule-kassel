import graphics.GraphicReader;
import javafx.application.Application;
import javafx.stage.Stage;
import language.TextReader;
import models.Screens.StartScreen;
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
        StartScreen startScreen = new StartScreen(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
