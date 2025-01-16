package levels;

import java.util.ArrayList;

import HUD.HUD;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.CollisionDetection;
import models.Item;
import models.Screens.GameoverScreen;
import models.entities.Player;
import models.entities.Policeman;
import models.fpsCounter.FPSCounter;
import models.tiles.Tile;
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
    private ArrayList<Tile> tiles;
    private ArrayList<Item> items;
    private AnimationTimer timer;
    private HUD hud;
    private boolean itemInRange;
    
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
            this.tiles = initializeTiles(this.rootPane, mapName);
            this.items = intitializeItems(this.rootPane, mapName);
            this.player = initializePlayer(this.rootPane);    
            this.policemen = initializePoliceman(rootPane, mapName);
            if(this.policemen.size() > 0) {
                for(int i = 0; i < this.policemen.size(); i++) {
                    this.policemen.get(i).getWaypoints().addAll(MapReader.readWaypoints(mapName, i));
                }
            }
            this.keyboardListener = new KeyboardListener(this.stage, this.scene);
            
            this.hud = new HUD(rootPane);
            this.itemInRange = false;
            
        }
        
        //#region ini functions


    private ArrayList<Tile> initializeTiles(Pane pane, String mapName) {
        // Hindernisse und Wände hinzufügen
        ArrayList<Tile> tiles = MapReader.readTiles(mapName);

        for(Tile tile : tiles) {
            pane.getChildren().addAll(tile.getHitbox());
        }

        return tiles;
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
        ArrayList<Policeman> policemen = MapReader.readPolicemen(mapName);
    
        for (Policeman policeman : policemen) {
            pane.getChildren().add(policeman.getVisionCircle());
            pane.getChildren().add(policeman.getHitbox());
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
    //#endregion

    public void start() {
        
        ArrayList<Rectangle> tileHitboxes = new ArrayList<>();

        for(Tile tile : tiles) {
            tileHitboxes.add(tile.getHitbox());
        }
        
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(Policeman policeman : policemen) {
                    if(CollisionDetection.checkCollisionWithPoliceman(player, policeman) && !keyboardListener.getGodMode()) {
                        this.stop();
                        GameoverScreen gmScreen = new GameoverScreen(stage, mapName);
                        gmScreen.setDeathMessage("You got caught!");
                    }

                    if(keyboardListener.getShiftPressed()) {
                        player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")));
                    } else {
                        player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")));
                    }

                }

                player.updatePlayerPosition(
                    (Rectangle) rootPane.lookup("#playerRectangle"),
                    tileHitboxes,
                    keyboardListener
                );

                Item nearestItem = getNearestItem();
                if(itemInRange) {
                    hud.printItemCollectable(nearestItem);
                } else {
                    hud.hideItemCollectable();
                }

                if(itemInRange && keyboardListener.getCollectItemPressed()) {
                    player.collectItem(rootPane, items, nearestItem, keyboardListener);
                }

                if(keyboardListener.getGetCoordinates()) {
                    System.out.println(String.format("Player(%.1f | %.1f)", player.getX(), player.getY()));
                }
            }
        };
        timer.start();
        
        
        for (Policeman policeman : policemen) {
            policeman.followPath(rootPane);
        }
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
        tiles = null;
        items = null;
        keyboardListener = null;

    }

    public void addFPSCounter() {
        FPSCounter fpsCounter = new FPSCounter();
        
        this.rootPane.getChildren().add(fpsCounter.createFPSCounterLabel());
    }

    private Item getNearestItem() {
        Item nearestItem = null;
        double minDistance = Double.MAX_VALUE;
    
        // Find the nearest item
        for (Item item : items) {
            double distance = calculateDistance(item);
    
            if (distance < minDistance) {
                nearestItem = item;
                minDistance = distance;
            }
        }
    
        if (nearestItem == null) {
            this.itemInRange = false; // Kein Item vorhanden
            if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NO_ITEM_IN_LEVEL_OUTPUT"))) {
                System.out.println("No item in level");
            }
        } else {
            this.itemInRange = minDistance < Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_COLLECT_RANGE"));
    
            if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_OUTPUT"))) {
                System.out.println(String.format("Nearest Item: '%s'", nearestItem.toString()));
            }
            if (this.itemInRange && Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_IN_RANGE_OUTPUT"))) {
                System.out.println(String.format("Item: '%s' is in range", nearestItem.toString()));
            }
        }
        return nearestItem;
    }
    

    private double calculateDistance(Item item) {
        return Math.sqrt(Math.pow(this.player.getX() - item.getX(), 2) + Math.pow(this.player.getY() - item.getY(), 2));
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

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }
    
    public AnimationTimer getTimer() {
        return timer;
    }

    //#endregion
}