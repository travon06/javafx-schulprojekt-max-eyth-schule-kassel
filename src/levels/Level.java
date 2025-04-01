package levels;

import java.util.ArrayList;
import java.util.Locale;

import HUD.HUD;
import goal.Finish;
import graphics.Graphics;
import items.Item;
import items.Key;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import language.Texts;
import models.CollisionDetection;
import models.CountdownTimer;
import models.Gate;
import models.Screens.EndScreen;
import models.Screens.GameoverScreen;
import models.Screens.LevelSelection;
import models.Screens.StartScreen;
import models.entities.Player;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;
import utils.mapConfig.MapReader;
import utils.statistics.Statistics;

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
    private String mapNameToTrigger;
    private ArrayList<Gate> gates;
    private boolean isLastLevel;
    private long lastUpdate;
    private int frameCount;
    private long lastFpsCheck;
    private int currentFps;
    private CountdownTimer countdownTimer;
    private int timeToSurvive;
    private boolean stopped;
    private long startTime;
    private long endTime;
    
    public Level(Stage stage, String mapName, String mapNameToTriger) {
        this.startTime = 0;
        this.endTime = 0;
        this.stage = stage; 
        this.stage.setResizable(false);
        this.mapName = mapName;
        this.mapNameToTrigger = mapNameToTrigger;
        this.finished = false;
        this.rootPane = new Pane();
        this.lastUpdate = 0;
        this.frameCount = 0;
        this.lastFpsCheck = 0;
        this.currentFps = 0;   
        this.stopped = false;
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))       
            );        
            this.tiles = initializeTiles(this.rootPane, mapName);
            this.items = intitializeItems(this.rootPane, mapName);
            this.policemen = initializePoliceman(rootPane, mapName);
            this.finish = initializeGoal(rootPane);
            initializeGates(rootPane);    
            initializeIsLastLevel(mapName);
            this.hud = new HUD(rootPane);
            if(this.policemen.size() > 0) {
                for(int i = 0; i < this.policemen.size(); i++) {
                    this.policemen.get(i).getWaypoints().addAll(MapReader.readWaypoints(mapName, i));
                }
            }
            if(finish.getGoal().equals("COLLECT_ITEMS")) {
                this.items.addAll(initializeItemsToCollect(rootPane));
                
            } else if(finish.getGoal().equals("SURVIVE")) {
                this.timeToSurvive = MapReader.readTimeToSurvive(mapName);
                this.countdownTimer = new CountdownTimer(hud.getTimerLabel(), timeToSurvive);
            }

            ArrayList<Item> keys = new ArrayList<>();
            for(Item item : items) {
                if(item instanceof Key) {
                    keys.add(item);
                }
            }
            
            for(int i = 0; i < gates.size(); i++) {
                gates.get(i).setOpeningKey((Key) keys.get(i));
            }
            this.player = initializePlayer(this.rootPane);
            this.keyboardListener = new KeyboardListener(this.stage, this.scene);
            
            this.itemInRange = false;
            
        }
        
    //#region
    private ArrayList<Tile> initializeTiles(Pane pane, String mapName) {
        ArrayList<Tile> tiles = MapReader.readTiles(mapName);

        for(Tile tile : tiles) {
            pane.getChildren().addAll(tile.getHitbox());
            if(tile.getImageView() != null) {
                pane.getChildren().addAll(tile.getImageView());
            }
        }

        return tiles;
    }

    private void initializeIsLastLevel(String mapName) {
        this.isLastLevel = MapReader.readIsLastLevel(mapName);
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

    private void initializeGates(Pane pane) {
        this.gates = MapReader.readGates(mapName);
        for(Gate gate : gates) {
            pane.getChildren().addAll(gate.getHitbox(), gate.getImageView());
        }
    }

    private Finish initializeGoal(Pane pane) {
        this.finish = MapReader.readFinish(mapName);
        pane.getChildren().addAll(finish.getHitbox());
        return finish;
    }

    private ArrayList<Item> initializeItemsToCollect(Pane pane) {
        ArrayList<Item> itemsToCollect = MapReader.readItemsToCollect(mapName, items);
        this.finish.setItemsToCollect(itemsToCollect);
        for(Item i : itemsToCollect) {
            pane.getChildren().addAll(i.getHitbox(), i.getImageView());
        }
        return itemsToCollect;
    }
    //#endregion ini functions

    public void start() {
        this.startTime = System.nanoTime();
        
        if(finish.getGoal().equals("SURVIVE")) {
            countdownTimer.start();
        }
        hud.getGoalLabel().setText(formatGoalLabel());

        long nanosPerUpdate = 1_000_000_000L / 300;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // if (now - lastUpdate >= nanosPerUpdate) {
                //     lastUpdate = now;
                //     update();
                //     trackFps(now);
                // }
                update();
                if(!stopped) {
                    trackFps(now);
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

    public void update() {
        for(Policeman policeman : policemen) {
            if(CollisionDetection.checkCollisionWithPoliceman(player, policeman) && player.getVissible() && !keyboardListener.getGodMode()) {
                Stage newStage = this.stage;
                this.stop(); 
                GameoverScreen gmScreen = new GameoverScreen(newStage, mapName);
                gmScreen.setDeathMessage(Texts.getTextByName("gameoverScreenMessageLabel").getTextInLanguage());
                return;
            }  
        }
        
        for (Gate gate : gates) {
            double distance = player.getHitbox().getBoundsInParent().getMinX() - gate.getHitbox().getBoundsInParent().getMinX();
            distance = Math.hypot(distance, player.getHitbox().getBoundsInParent().getMinY() - gate.getHitbox().getBoundsInParent().getMinY());
        
            if (distance <= 100) {
                hud.printGateMessage(gate);
                if(keyboardListener.getInteractPressed() && gate.getAccessible()) {
                    gate.setOpen(true);
                    rootPane.getChildren().remove(gate.getImageView());
                    gate.setImageView(new ImageView(new Image(Graphics.getGraphicUrl(ConfigArguments.getConfigArgumentValue("GATE_GRAPHIC_OPEN")))));
                    gate.getImageView().setX(gate.getX());
                    gate.getImageView().setY(gate.getY());
                    gate.getImageView().setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("GATE_WIDTH")));
                    gate.getImageView().setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("GATE_HEIGHT")));
                    rootPane.getChildren().add(gate.getImageView());
                }
            } else {
                hud.hideGateMessage();
            }
        }
        

        if(keyboardListener.getEscPressed()) {
            Stage newStage = this.stage;
            this.stop();
            new StartScreen(newStage);
            return;
        }
        
        if(keyboardListener.getSprintPressed() && player.getBoosted()) {
            player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOOSTED_SPRINT_SPEED")));
        } else if(keyboardListener.getSprintPressed() && !player.getBoosted()) {
            player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")));
        } else if (!keyboardListener.getSprintPressed() && player.getBoosted()) {
            player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOOSTED_SPEED")));
        } else {
            player.setSpeed(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")));
        }
        
        player.updatePlayerPosition(
            (Rectangle) rootPane.lookup("#playerRectangle"),
            gates,
            tiles,
            keyboardListener
        );

        Item nearestItem = getNearestItem();
        if(itemInRange) {
            hud.printItemCollectable(nearestItem);
        } else {
            hud.hideItemCollectable();
        }


    



        if(itemInRange && keyboardListener.getInteractPressed()) {
            nearestItem.use(rootPane, player, gates);
            player.collectItem(rootPane, items, finish.getItemsToCollect(), nearestItem, keyboardListener, finish);
            if(nearestItem instanceof Key) {
                for(Gate gate : gates) {
                    if(gate.getOpeningKey() == nearestItem) {
                        gate.setAccessible(true);
                    }
                }
            }
        }

        if(keyboardListener.getGetCoordinates()) {
            System.out.println(String.format("Player(%d | %d)", player.getX(), player.getY()));
        }

        if(finish.getAccessible()) {
            if(finish.getGoal().equals("COLLECT_ITEMS")) {
                hud.getGoalLabel().setText(Texts.getTextByName("HUDGoalLabelFinishedCollectItems").getTextInLanguage());
            } else if(finish.getGoal().equals("SURVIVE")) {
                hud.getGoalLabel().setText(Texts.getTextByName("HUDGoalLabelFinishedSurvive").getTextInLanguage());

            }
            if(CollisionDetection.checkCollisionWithFinish(player, finish) && keyboardListener.getInteractPressed()) {
                finished = true;
                if(!isLastLevel) {
                    for(int i = 0; i < LevelSelection.getMapNames().size(); i++) {
                        LevelSelection.disableButton(false, LevelSelection.getMapNames().indexOf(mapName) +1);
                    }
                    Level newLewel = new Level(this.stage, mapNameToTrigger, MapReader.getNextLevel(mapNameToTrigger));


                    for(int i = 0; i < MapReader.MAPNAMES.size(); i++) {
                        if(mapName.equals(MapReader.MAPNAMES.get(i)) && i >= Integer.parseInt(Statistics.getStatisticValue("LAST_LEVEL_INDEX")))  {
                            System.out.println(i);
                            Statistics.setStatisticValue("LAST_LEVEL_INDEX", String.format("%d", i+1));
                        }


                    }

                    this.stop();
                    newLewel.start();
                    return;
                } else {
                    for(int i = 0; i < MapReader.MAPNAMES.size(); i++) {
                        if(mapName.equals(MapReader.MAPNAMES.get(i)) && i > Integer.parseInt(Statistics.getStatisticValue("LAST_LEVEL_INDEX")))  {
                            Statistics.setStatisticValue("LAST_LEVEL_INDEX", String.format("%d", i+1));
                        }


                    }

                    Stage newStage = this.stage;
                    this.stop();
                    new EndScreen(newStage);
                    return;
                }
                

            }
        }

        finish.checkState(countdownTimer);
    }

    private void trackFps(long now) {
        frameCount++;

        if (now - lastFpsCheck >= 1_000_000_000L) {
            currentFps = frameCount;
            frameCount = 0;
            lastFpsCheck = now;
            if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_LOG_FPS"))) {
                System.out.println("FPS: " + currentFps);
            }
            hud.getFpsLabel().setText(String.format("FPS: %d", currentFps));
        }
    }

    public void stop() {
        this.endTime = System.nanoTime();

        double timeInLevel = (endTime - startTime) / 1_000_000_000.0 / 60.0;
        double minutesPlayed = Double.parseDouble(Statistics.getStatisticValue("MINUTES_PLAYED"));
        Statistics.setStatisticValue("MINUTES_PLAYED", String.format(Locale.US, "%.2f", minutesPlayed + timeInLevel));


        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
    
        if (policemen != null) {
            policemen.clear();
            policemen = null;
        }
    
        if (player != null) {
            rootPane.getChildren().remove(player.getHitbox());
            rootPane.getChildren().remove(player.getImage());
            player = null;
        }
    
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
    
        if (finish != null) {
            rootPane.getChildren().remove(finish.getHitbox());
            finish = null;
        }
    
        if (hud != null) {
            hud = null;
        }
    
        if (keyboardListener != null) {
            keyboardListener = null;
        }
        
        if(rootPane != null) {
            rootPane.getChildren().clear();
        }
        rootPane = null;
        scene = null;
    
        if (stage != null) {
            stage = null;
        }

        this.stopped = true;
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
            this.itemInRange = false;
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

    private String formatGoalLabel() {

        String goalString = "";

        if(finish.getGoal().equals("COLLECT_ITEMS")) {
            goalString = Texts.getTextByName("HUDGoalLabelUnfinishedCollectItems").getTextInLanguage() + ": (";
            for(int i = 0; i < finish.getItemsToCollect().size(); i++) {
                goalString += finish.getItemsToCollect().get(i).getName();
                if(i < finish.getItemsToCollect().size()  - 1) {
                    goalString += ", ";
                }
            }
            goalString += ")";
        } else if(finish.getGoal().equals("SURVIVE")) {
            goalString = Texts.getTextByName("HUDGoalLabelUnfinishedSurvive").getTextInLanguage();
        }
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

    public void setCountdownTimer(CountdownTimer countdownTimer) {
        this.countdownTimer = countdownTimer;
    }

    public CountdownTimer getCountdownTimer() {
        return countdownTimer;
    }
    
    //#endregion
}