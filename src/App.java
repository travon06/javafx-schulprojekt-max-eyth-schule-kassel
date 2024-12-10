import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import levels.Level;
import models.Item;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;

public class App extends Application {

    private static final int SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT;

    private List<Rectangle> collisionRectangles;
    private List<Item> items;
    private Scene scene;

    static {
        // Konfiguration laden
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
        SCREEN_WIDTH = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        SCREEN_HEIGHT = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    }

    @Override
    public void start(Stage primaryStage) {
        Level level = new Level(primaryStage, "level2");
        level.getKeyboardListener().handleKeyboardInputs(level.getPlayer(), true, true, true);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
