import javafx.application.Application;
import javafx.stage.Stage;
import levels.Level;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;

public class App extends Application {

    static {
        // load configurations
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
    }

    @Override
    public void start(Stage primaryStage) {
        String mapName = "level2";
        Stage levelStage = new Stage();
        Level level = new Level(levelStage, mapName);
        levelStage.setTitle(mapName);
        level.addFPSCounter();
        level.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
