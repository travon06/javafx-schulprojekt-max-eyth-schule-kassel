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
        Stage level2Stage = new Stage();
        

        Level level2 = new Level(level2Stage, "level2");
        level2.addFPSCounter();
        level2.start();
        
    }

    public static void main(String[] args) {

        launch(args);
    }
}
