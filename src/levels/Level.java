package levels;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Item;
import models.entities.Player;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;
import utils.mapConfig.MapReader;

public class Level {
    private Scene scene;
    private Stage stage;
    private Pane rootPane;
    private String mapName;
    private Player player;
    private KeyboardListener keyboardListener;
    private List<Rectangle> obstacles;
    private List<Item> items;

    public Level(Stage stage, String mapName) {
        this.stage = stage;
        this.mapName = mapName;
        this.rootPane = new Pane();
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))       
            );
        this.obstacles = initializeObstacles(this.rootPane, mapName);
        this.items = intitializeItems(this.rootPane, mapName);
        this.player = initializePlayer(this.rootPane);
        this.keyboardListener = new KeyboardListener(this.stage, this.scene);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.updatePlayerPosition(
                        (Rectangle) rootPane.lookup("#playerRectangle"),
                        obstacles,
                        keyboardListener
                );
                if(keyboardListener.getCollectItemPressed()) {
                    player.collectItem(items, keyboardListener);
                }
            }
        };
        timer.start();

        this.stage.setScene(this.scene);
        this.stage.setTitle(this.mapName);
        this.stage.show();
    }

    private List<Rectangle> initializeObstacles(Pane pane, String mapName) {
        // Hindernisse und Wände hinzufügen
        List<Rectangle> obstacles = MapReader.readObstacles(mapName);

        for(Rectangle rectangle : obstacles) {
            pane.getChildren().addAll(rectangle);
        }

        return obstacles;
    }

    private List<Item> intitializeItems(Pane pane, String mapName) {
        List<Item> items = MapReader.readItems(mapName);

        for(Item item : items) {
            item.getHitbox().setFill(Color.RED);
            pane.getChildren().add(item.getHitbox());
        }

        return items;
    }

    private Player initializePlayer(Pane pane) {
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

    //#region getter & setter
    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public KeyboardListener getKeyboardListener() {
        return this.keyboardListener;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Pane getRootPane() {
        return this.rootPane;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public Scene getScene() {
        return this.scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    //#endregion
}
