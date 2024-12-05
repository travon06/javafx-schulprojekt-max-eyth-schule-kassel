import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Item;
import models.entities.Player;
import models.fpsCounter.FPSCounter;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;
import utils.keyboard.Keyboard;
import utils.mapConfig.MapReader;
import java.util.List;

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
        // Initialisiere das Keyboard für Eingaben
        Keyboard.setStage(primaryStage);

        //
        Pane rootPane = new Pane();

        // Initialisiere Spieler
        Player player = initializePlayer(rootPane);

        // Hindernisse und Wände erstellen
        collisionRectangles = initializeObstacles(rootPane);
        items = intitializeItems(rootPane);


        // FPS-Anzeige hinzufügen
        FPSCounter fpsCounter = new FPSCounter();
        Label fpsCounterLabel = fpsCounter.createFPSCounterLabel();
        rootPane.getChildren().add(fpsCounterLabel);

        // Szene erstellen
        scene = new Scene(rootPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        Keyboard.setScene(scene);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Tastatureingaben aktivieren
        Keyboard.handleKeyboardInputs(
                player,
                Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_PLAYER_MOVEMENT")),
                Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("EXIT_ON_ENTER")),
                Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ALLOW_COLLECT_ITEM"))
        );

        // Animation Timer für kontinuierliche Updates
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.updatePlayerPosition(
                        (Rectangle) rootPane.lookup("#playerRectangle"),
                        collisionRectangles
                );
                if(Keyboard.getCollectItemPressed()) {
                    player.collectItem(items);
                }
            }
        };
        timer.start();
    }

    private Player initializePlayer(Pane pane) {
        // Spieler erstellen und visuelles Rechteck hinzufügen
        Player player = new Player(
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_HEALTH")),
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")),
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")),
                Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_COLLECT_RANGE")),
                100, // Startposition X
                200  // Startposition Y
        );

        Rectangle playerRectangle = new Rectangle(50, 50, Color.BLUE);
        playerRectangle.setId("playerRectangle");
        playerRectangle.setX(100); // Startposition
        playerRectangle.setY(100); // Startposition

        pane.getChildren().add(playerRectangle);
        return player;
    }

    private List<Rectangle> initializeObstacles(Pane pane) {
        // Hindernisse und Wände hinzufügen
        List<Rectangle> obstacles = MapReader.readObstacles("level2");

        for(Rectangle rectangle : obstacles) {
            pane.getChildren().addAll(rectangle);
        }

        return obstacles;
    }

    private List<Item> intitializeItems(Pane pane) {
        List<Item> items = MapReader.readItems("level2");

        for(Item item : items) {
            item.getHitbox().setFill(Color.RED);
            pane.getChildren().add(item.getHitbox());
        }

        return items;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
