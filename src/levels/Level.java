package levels;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Item;
import models.entities.Player;
import models.entities.Policeman;
import models.fpsCounter.FPSCounter;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;
import utils.mapConfig.MapReader;

public class Level {
    private Scene scene;
    private Stage stage;
    private Pane rootPane;
    private String mapName;
    private Player player;
    private ArrayList<Policeman> policemen;
    private KeyboardListener keyboardListener;
    private ArrayList<Rectangle> obstacles;
    private ArrayList<Item> items;
    private AnimationTimer timer;

    public Level(Stage stage, String mapName) {


        this.stage = stage;
        this.stage.setResizable(false);

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
        this.policemen = initializePoliceman(rootPane, mapName);
        if(this.policemen.size() > 0) {
            for(int i = 0; i < this.policemen.size(); i++) {
                this.policemen.get(i).getWaypoints().addAll(MapReader.readWaypoints(mapName, i));
            }
        }
        this.keyboardListener = new KeyboardListener(this.stage, this.scene);
    

    }


    private ArrayList<Rectangle> initializeObstacles(Pane pane, String mapName) {
        // Hindernisse und Wände hinzufügen
        ArrayList<Rectangle> obstacles = MapReader.readObstacles(mapName);

        for(Rectangle rectangle : obstacles) {
            pane.getChildren().addAll(rectangle);
        }

        return obstacles;
    }

    private ArrayList<Item> intitializeItems(Pane pane, String mapName) {
        ArrayList<Item> items = MapReader.readItems(mapName);

        for(Item item : items) {
            item.getHitbox().setFill(Color.RED);
            pane.getChildren().add(item.getHitbox());
        }

        return items;
    }

    private ArrayList<Policeman> initializePoliceman(Pane pane, String mapName) {
        ArrayList<Policeman> policemen = MapReader.readpolicemen(mapName);
    
        for (Policeman policeman : policemen) {
            pane.getChildren().addAll(policeman.getHitbox());
        }

        return policemen;
    }
    

    private Player initializePlayer(Pane pane) {
        Player player = new Player(
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_HEALTH")),
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")),
                Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")),
                Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_COLLECT_RANGE")),
                new Rectangle(50, 50),
                MapReader.readPlayerStartCoordinates(mapName)[0],
                MapReader.readPlayerStartCoordinates(mapName)[1]  
        );

        player.getHitbox().setId("playerRectangle");
        player.getHitbox().setX(MapReader.readPlayerStartCoordinates(mapName)[0]);
        player.getHitbox().setY(MapReader.readPlayerStartCoordinates(mapName)[1]); 
        player.getHitbox().setFill(Color.BLUE);
        pane.getChildren().add(player.getHitbox());
        return player;
    }

    public void start() {
        for (Policeman policeman : policemen) {
            policeman.followPath(rootPane);
        }
        
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.updatePlayerPosition(
                    (Rectangle) rootPane.lookup("#playerRectangle"),
                    obstacles,
                    keyboardListener
                );
    
                if (keyboardListener.getCollectItemPressed()) {
                    player.collectItem(rootPane, items, keyboardListener);
                }
            }
        };
        timer.start();

        boolean enablePlayerMovement = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_PLAYER_MOVEMENT"));
        boolean exitOnEnter = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_EXIT_ON_ENTER"));
        boolean allowCollectItem = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_COLLECT_ITEM"));
        
        this.keyboardListener.handleKeyboardInputs(player, enablePlayerMovement, exitOnEnter, allowCollectItem);
    
        this.stage.setScene(this.scene);
        this.stage.setTitle(this.mapName);
        this.stage.show();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        
        rootPane.getChildren().clear();
        scene = null;
        this.stage.close();
        stage = null;
        rootPane = null;
        player = null;
        obstacles = null;
        items = null;
        keyboardListener = null;

    }

    public void addFPSCounter() {
        FPSCounter fpsCounter = new FPSCounter();
        
        this.rootPane.getChildren().add(fpsCounter.createFPSCounterLabel());
    }
    

    //#region getter & setter
    public void setkeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public KeyboardListener getkeyboardListener() {
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

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setObstacles(ArrayList<Rectangle> obstacles) {
        this.obstacles = obstacles;
    }

    public ArrayList<Rectangle> getObstacles() {
        return obstacles;
    }
    
    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }
    
    public AnimationTimer getTimer() {
        return timer;
    }

    //#endregion
}
