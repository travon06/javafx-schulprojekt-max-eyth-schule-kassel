package levels;

import java.util.ArrayList;

import HUD.HUD;
import goal.Finish;
import items.Item;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import language.Texts;
import models.CollisionDetection;
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
    private Finish finish;
    private ArrayList<Item> itemsToCollect;
    private boolean finished;
    private ArrayList<Level> levels;
    private boolean isAvailable;
    
    public Level(Stage stage, String mapName, ArrayList<Level> levels) {
        this.isAvailable = true;
        this.stage = stage; 
        this.stage.setResizable(false);
        this.levels = levels;
        this.mapName = mapName;
        this.finished = false;
        this.rootPane = new Pane();
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))       
            );        
            this.tiles = initializeTiles(this.rootPane, mapName);
            this.items = intitializeItems(this.rootPane, mapName);
            this.policemen = initializePoliceman(rootPane, mapName);
            this.finish = initializeGoal(rootPane);
            this.itemsToCollect = initializeItemsToCollect();
            this.player = initializePlayer(this.rootPane);    
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
            if(tile.getImageView() != null) {
                pane.getChildren().addAll(tile.getImageView());
            }
        }

        return tiles;
    }

    private ArrayList<Item> intitializeItems(Pane pane, String mapName) {
        ArrayList<Item> items = MapReader.readItems(mapName);

        for(Item item : items) {
            item.getHitbox().setFill(Color.RED);
            if(item.getImageView() == null) {
                pane.getChildren().add(item.getHitbox());
            } else {
                pane.getChildren().addAll(item.getHitbox(), item.getImageView());

            }   
        }

        return items;
    }

    private ArrayList<Policeman> initializePoliceman(Pane pane, String mapName) {
        ArrayList<Policeman> policemen = MapReader.readPolicemen(mapName);
    
        for (Policeman policeman : policemen) {
            pane.getChildren().addAll(policeman.getHitbox(), policeman.getImageView());
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
        pane.getChildren().addAll(player.getHitbox(), player.getImage());
        return player;
    }

    private Finish initializeGoal(Pane pane) {
        this.finish = MapReader.readFinish(mapName);
        pane.getChildren().addAll(finish.getHitbox());
        return finish;
    }

    private ArrayList<Item> initializeItemsToCollect() {
        this.finish.setItemsToCollect(MapReader.readItemsToCollect(mapName, items));
        return itemsToCollect;
    }
    //#endregion

    public void start() {
        
        ArrayList<Rectangle> tileHitboxes = new ArrayList<>();

        for(Tile tile : tiles) {
            tileHitboxes.add(tile.getHitbox());
        }

        if(finish.getGoal().equals("COLLECT_ITEMS")) {
            hud.getGoalLabel().setText(formatItemToCollectLabelMessage());
        }

        
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(Policeman policeman : policemen) {
                    if(CollisionDetection.checkCollisionWithPoliceman(player, policeman) && player.getVissible() && !keyboardListener.getGodMode()) {
                        this.stop();
                        GameoverScreen gmScreen = new GameoverScreen(stage, mapName, levels);
                        gmScreen.setDeathMessage(Texts.getTextByName("gameoverScreenMessageLabel").getTextInLanguage());
                    }
                    
                }
                
                if(keyboardListener.getShiftPressed() && player.getBoosted()) {
                    player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOOSTED_SPRINT_SPEED")));
                } else if(keyboardListener.getShiftPressed() && !player.getBoosted()) {
                    player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")));
                } else if (!keyboardListener.getShiftPressed() && player.getBoosted()) {
                    player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOOSTED_SPEED")));
                } else {
                    player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")));
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

                if(itemInRange && keyboardListener.getInteractPressed()) {
                    nearestItem.use(rootPane, player);
                    player.collectItem(rootPane, items, finish.getItemsToCollect(), nearestItem, keyboardListener, finish);
                }

                if(keyboardListener.getGetCoordinates()) {
                    System.out.println(String.format("Player(%d | %d)", player.getX(), player.getY()));
                }

                if(finish.getAccessible()) {
                    hud.getGoalLabel().setText(Texts.getTextByName("HUDGoalLabelFinished").getTextInLanguage());
                    if(CollisionDetection.checkCollisionWithFinish(player, finish) && keyboardListener.getInteractPressed()) {
                        finished = true;
                        // this.stop();
                        // System.out.println("jasdofjsbdc");
                        // Level nextLevel = levels.get(levels.indexOf(2)+1);
                        // nextLevel.addFPSCounter();
                        // nextLevel.start();

                    }
                }

                finish.checkState();

            }
        };
        timer.start();
        
        
        for (Policeman policeman : policemen) {
            policeman.followPath(rootPane);
        }
        boolean enablePlayerMovement = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_PLAYER_MOVEMENT"));
        boolean exitOnEnter = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_EXIT_ON_ENTER"));
        boolean allowCollectItem = Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("ENABLE_COLLECT_ITEM"));
        
        this.keyboardListener.handleKeyboardInputs(player, enablePlayerMovement, exitOnEnter, allowCollectItem, levels);
    
        this.stage.setScene(this.scene);
        this.stage.setTitle(this.mapName);
        this.stage.show();
    }

    public void stop() {
        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
    
        // Stoppe alle Polizisten und lösche sie aus der Szene
        if (policemen != null) {
            for (Policeman policeman : policemen) {
                // policeman.stop(); // Eine Methode, die Bewegungen der Polizisten stoppt
            }
            policemen.clear();
            policemen = null;
        }
    
        // Entferne den Spieler aus der Szene
        if (player != null) {
            rootPane.getChildren().remove(player.getHitbox());
            rootPane.getChildren().remove(player.getImage());
            player = null;
        }
    
        // Entferne Items
        if (items != null) {
            for (Item item : items) {
                rootPane.getChildren().remove(item.getHitbox());
                if (item.getImageView() != null) {
                    rootPane.getChildren().remove(item.getImageView());
                }
            }
            items.clear();
            items = null;
        }
    
        // Entferne Tiles
        if (tiles != null) {
            for (Tile tile : tiles) {
                rootPane.getChildren().remove(tile.getHitbox());
                if (tile.getImageView() != null) {
                    rootPane.getChildren().remove(tile.getImageView());
                }
            }
            tiles.clear();
            tiles = null;
        }
    
        // Entferne das Ziel
        if (finish != null) {
            rootPane.getChildren().remove(finish.getHitbox());
            finish = null;
        }
    
        // Entferne HUD
        if (hud != null) {
            // hud.clear(); // Methode zum Entfernen aller HUD-Elemente
            hud = null;
        }
    
        // Entferne Event-Listener
        if (keyboardListener != null) {
            // keyboardListener.removeAllListeners();
            keyboardListener = null;
        }
    
        // Lösche alle übrigen Elemente und setze die Szene zurück
        rootPane.getChildren().clear();
        rootPane = null;
        scene = null;
    
        // Schließe das Fenster
        if (stage != null) {
            stage.close();
            stage = null;
        }
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

    private String formatItemToCollectLabelMessage() {
        String goalString = Texts.getTextByName("HUDGoalLabelUnfinished").getTextInLanguage() + ": (";

        for(int i = 0; i < finish.getItemsToCollect().size(); i++) {
            goalString += finish.getItemsToCollect().get(i).getName();
            if(i < finish.getItemsToCollect().size()  - 1) {
                goalString += ", ";
            }
        }

        goalString += ")";

        return goalString;
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

    public void setFinish(Finish finish) {
        this.finish = finish;
    }
    
    public Finish getFinish() {
        return finish;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }

    public HUD getHud() {
        return hud;
    }

    public void setItemInRange(boolean itemInRange) {
        this.itemInRange = itemInRange;
    }

    public ArrayList<Item> getItemsToCollect() {
        return itemsToCollect;
    }

    public void setItemsToCollect(ArrayList<Item> itemsToCollect) {
        this.itemsToCollect = itemsToCollect;
    }

    public KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArrayList<Policeman> getPolicemen() {
        return policemen;
    }
    public void setPolicemen(ArrayList<Policeman> policemen) {
        this.policemen = policemen;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean getAvailable() {
        return this.isAvailable;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }
    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    
    //#endregion
}