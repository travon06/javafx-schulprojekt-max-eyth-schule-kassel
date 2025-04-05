package models.Screens;

import java.util.ArrayList;

import goal.Finish;
import graphics.Graphics;
import items.Coat;
import items.EnergyDrink;
import items.Item;
import items.ItemToCollect;
import items.Key;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import language.Texts;
import models.Gate;
import models.entities.Player;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.Waypoint;
import utils.config.ConfigArguments;
import utils.mapConfig.MapWriter;

public class MapMakerNew {
    private Scene scene;
    private Pane rootPane;
    private HBox hBox;
    private VBox vBoxInfo;
    private Label labelKeyBinds;
    private Label labelKeyBindsInfo;
    private Button buttonCreate;

    private static final int tileSize = 50;
    private static final int hBoxSize = 200;
    private static final int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
    private static final int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    private boolean dragging;
    private boolean spawnPointSet;
    private boolean goalFinishSet;
    private boolean showVboxInfo;
    private String goal;
    private MapWriter mapWriter;
    private int timeToSurvive;

    private ImageView emtyImageView;
    private ImageView tile;
    private ImageView tileIron;
    private ImageView police;
    private ImageView waypoint;
    private ImageView energyDrink;
    private ImageView coat;
    private ImageView spawnPoint;
    private ImageView gate;
    private ImageView key;
    private ImageView street;
    private ImageView streetCurve;
    private ImageView crossing;
    private ImageView crossing3;
    private ImageView rubber;
    private ImageView itemToCollectWeed;
    private ImageView itemToCollectKokain;
    private ImageView finish;
    private ImageView imageDragged;
    private ArrayList<Tile> tiles;
    private ArrayList<Policeman> policemans;
    private ArrayList<Item> items;
    private ArrayList<Gate> gates;
    private ArrayList<Key> keys;
    private ArrayList<Item> itemsToCollect;
    private Player player;
    private Finish goalFinish;

    public MapMakerNew(Stage stage, String goal) {
        this.rootPane = new Pane();
        this.scene = new Scene(this.rootPane, screenWidth, screenHeight);

        this.hBox = new HBox();
        this.hBox.setPrefSize(screenWidth, hBoxSize);
        this.hBox.setLayoutY(screenHeight - hBoxSize);
        this.hBox.setStyle("-fx-background-color:grey");

        this.vBoxInfo = new VBox(10);
        this.vBoxInfo.setStyle("-fx-background-color:lightgrey; -fx-padding:5px");
        this.labelKeyBinds = new Label(Texts.getTextByName("labelKeyBinds").getTextInLanguage());
        this.labelKeyBinds.setId("headline");
        this.labelKeyBindsInfo = new Label(String.format("Q = %s\nE = %s\n%s\n%s\nEnter = %s",
                                                        Texts.getTextByName("labelKeyBindsInfoQ").getTextInLanguage(),
                                                        Texts.getTextByName("labelKeyBindsInfoE").getTextInLanguage(),
                                                        Texts.getTextByName("labelKeyBindsInfoLeftClick").getTextInLanguage(),
                                                        Texts.getTextByName("labelKeyBindsInfoRightClick").getTextInLanguage(),
                                                        Texts.getTextByName("labelKeyBindsInfoHide").getTextInLanguage()));
        this.labelKeyBindsInfo.setId("info");
        this.vBoxInfo.getChildren().addAll(labelKeyBinds, labelKeyBindsInfo);

        this.dragging = false;
        this.spawnPointSet = false;
        this.goalFinishSet = false;
        this.showVboxInfo = true;
        this.timeToSurvive = 60;
        this.emtyImageView = new ImageView();
        this.imageDragged = emtyImageView;
        this.goal = goal;
        this.buttonCreate = new Button(Texts.getTextByName("buttonCreate").getTextInLanguage());
        this.buttonCreate.addEventFilter(KeyEvent.KEY_PRESSED, event -> { handelKeyInput(event); event.consume(); });
        this.mapWriter = new MapWriter();


        this.tiles = new ArrayList<>();
        this.policemans = new ArrayList<>();
        this.items = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.itemsToCollect = new ArrayList<>();

        this.tile = new ImageView(new Image(Graphics.getGraphicUrl("copperRooftop")));
        this.tile.setFitWidth(tileSize);
        this.tile.setFitHeight(tileSize);
        this.tileIron = new ImageView(new Image(Graphics.getGraphicUrl("ironRooftop")));
        this.tileIron.setFitWidth(tileSize);
        this.tileIron.setFitHeight(tileSize);
        this.street = new ImageView(new Image(Graphics.getGraphicUrl("streetUp")));
        this.street.setFitWidth(tileSize);
        this.street.setFitHeight(tileSize);
        this.streetCurve = new ImageView(new Image(Graphics.getGraphicUrl("streetCurve")));
        this.streetCurve.setFitWidth(tileSize);
        this.streetCurve.setFitHeight(tileSize);
        this.crossing = new ImageView(new Image(Graphics.getGraphicUrl("crossing")));
        this.crossing.setFitWidth(tileSize);
        this.crossing.setFitHeight(tileSize);
        this.crossing3 = new ImageView(new Image(Graphics.getGraphicUrl("crossing3")));
        this.crossing3.setFitWidth(tileSize);
        this.crossing3.setFitHeight(tileSize);

        this.police = new ImageView(new Image(Graphics.getGraphicUrl("policeman")));
        this.police.setFitWidth(tileSize);
        this.police.setFitHeight(tileSize);
        this.police.setPickOnBounds(true);
        this.waypoint = new ImageView(new Image(Graphics.getGraphicUrl("flag")));
        this.waypoint.setFitWidth(tileSize);
        this.waypoint.setFitHeight(tileSize);

        this.energyDrink = new ImageView(new Image(Graphics.getGraphicUrl("energyDrink")));
        this.energyDrink.setFitWidth(tileSize);
        this.energyDrink.setFitHeight(tileSize);
        this.energyDrink.setPickOnBounds(true);
        this.coat = new ImageView(new Image(Graphics.getGraphicUrl("coat")));
        this.coat.setFitWidth(tileSize);
        this.coat.setFitHeight(tileSize);
        this.coat.setPickOnBounds(true);
        this.itemToCollectWeed = new ImageView(new Image(Graphics.getGraphicUrl("grasItem")));
        this.itemToCollectWeed.setFitWidth(tileSize);
        this.itemToCollectWeed.setFitHeight(tileSize);
        this.itemToCollectWeed.setPickOnBounds(true);
        this.itemToCollectKokain = new ImageView(new Image(Graphics.getGraphicUrl("sussyCocain")));
        this.itemToCollectKokain.setFitWidth(tileSize);
        this.itemToCollectKokain.setFitHeight(tileSize);
        this.itemToCollectKokain.setPickOnBounds(true);

        this.spawnPoint = new ImageView(new Image(Graphics.getGraphicUrl("playerGreen")));
        this.spawnPoint.setFitWidth(tileSize);
        this.spawnPoint.setFitHeight(tileSize);
        this.spawnPoint.setPickOnBounds(true);

        this.gate = new ImageView(new Image(Graphics.getGraphicUrl("gateClosed")));
        this.gate.setFitWidth(tileSize * 2);
        this.gate.setFitHeight(tileSize);
        this.gate.setPickOnBounds(true);
        this.key = new ImageView(new Image(Graphics.getGraphicUrl("key")));
        this.key.setFitWidth(tileSize);
        this.key.setFitHeight(tileSize);

        this.finish = new ImageView(new Image(Graphics.getGraphicUrl("finishGold")));
        this.finish.setFitWidth(tileSize);
        this.finish.setFitHeight(tileSize);
        this.finish.setPickOnBounds(true);

        this.rubber = new ImageView(new Image(Graphics.getGraphicUrl("rubber")));
        this.rubber.setFitWidth(tileSize);
        this.rubber.setFitHeight(tileSize);
        this.rubber.setPickOnBounds(true);



        setImageViews();
        setUpMouseMovements();

        stage.setResizable(false);

        this.rootPane.getChildren().addAll(hBox, vBoxInfo, tile, tileIron, street, streetCurve, crossing, crossing3, police, waypoint, energyDrink, coat, spawnPoint, gate, key, finish, itemToCollectWeed, itemToCollectKokain, rubber, buttonCreate);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/mapMaker.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(Texts.getTextByName("mapMaker").getTextInLanguage());
        stage.show();
        this.vBoxInfo.setLayoutX(screenWidth - vBoxInfo.getWidth());
    }

    private void setUpMouseMovements() {
        this.tile.setOnMouseClicked(event -> startDragging(tile, event));
        this.tileIron.setOnMouseClicked(event -> startDragging(tileIron, event));
        this.street.setOnMouseClicked(event -> startDragging(street, event));
        this.streetCurve.setOnMouseClicked(event -> startDragging(streetCurve, event));
        this.crossing.setOnMouseClicked(event -> startDragging(crossing, event));
        this.crossing3.setOnMouseClicked(event -> startDragging(crossing3, event));
        this.police.setOnMouseClicked(event -> startDragging(police, event));
        this.energyDrink.setOnMouseClicked(event -> startDragging(energyDrink, event));
        this.coat.setOnMouseClicked(event -> startDragging(coat, event));
        this.spawnPoint.setOnMouseClicked(event -> startDragging(spawnPoint, event));
        this.gate.setOnMouseClicked(event -> startDragging(gate, event));
        this.finish.setOnMouseClicked(event -> startDragging(finish, event));
        this.itemToCollectWeed.setOnMouseClicked(event -> startDragging(itemToCollectWeed, event));
        this.itemToCollectKokain.setOnMouseClicked(event -> startDragging(itemToCollectKokain, event));
        this.rubber.setOnMouseClicked(event -> startDragging(rubber, event));

        this.buttonCreate.setOnAction(event -> {
            if(spawnPointSet && goalFinishSet) {
                this.items.addAll(keys);
                mapWriter.createMap("test", new int[]{this.player.getX(), this.player.getY()}, this.tiles, this.items, this.policemans, this.gates, this.goalFinish, this.itemsToCollect, this.timeToSurvive);
            }
        });


        this.scene.setOnKeyPressed(this::handelKeyInput);
        this.scene.setOnMouseMoved(this::handleMouseMovement);
        this.scene.setOnMousePressed(event -> {
            if(imageDragged == rubber && event.getButton() != MouseButton.SECONDARY) handleRubberPressed(event);
            handleMousePressed(event);
        });
        this.scene.setOnMouseDragged(event -> {
            if(imageDragged == rubber && event.getButton() != MouseButton.SECONDARY) handleRubberPressed(event);
            handleMouseMovement(event);
            if(isWithinBounds() && isTile()) placeObjekt(event);
        });
    }

    private void setImageViews() {
        this.imageDragged = emtyImageView;
        this.dragging = false;

        this.tile.setLayoutX(screenWidth * 1 / 8 - tileSize / 2);
        this.tile.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.tileIron.setLayoutX(screenWidth * 2 / 8 - tileSize / 2);
        this.tileIron.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.street.setLayoutX(screenWidth * 3 / 8 - tileSize / 2);
        this.street.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.streetCurve.setLayoutX(screenWidth * 4 / 8 - tileSize / 2);
        this.streetCurve.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.crossing.setLayoutX(screenWidth * 5 / 8 - tileSize / 2);
        this.crossing.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.crossing3.setLayoutX(screenWidth * 6 / 8 - tileSize / 2);
        this.crossing3.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);
        this.police.setLayoutX(screenWidth * 7 / 8 - tileSize / 2);
        this.police.setLayoutY(screenHeight - hBoxSize * 3 / 4 - tileSize / 2);

        this.energyDrink.setLayoutX(screenWidth * 1 / 8 - tileSize / 2);
        this.energyDrink.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.coat.setLayoutX(screenWidth * 2 / 8 - tileSize / 2);
        this.coat.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.spawnPoint.setLayoutX(screenWidth * 3 / 8 - tileSize / 2);
        this.spawnPoint.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.gate.setLayoutX(screenWidth * 3.84 / 8 - tileSize / 2);
        this.gate.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.finish.setLayoutX(screenWidth * 5 / 8 - tileSize / 2);
        this.finish.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.itemToCollectWeed.setLayoutX(screenWidth * 6 / 8 - tileSize / 2);
        this.itemToCollectWeed.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);
        this.itemToCollectKokain.setLayoutX(screenWidth * 7 / 8 - tileSize / 2);
        this.itemToCollectKokain.setLayoutY(screenHeight - hBoxSize * 1 / 4 - tileSize / 2);

        this.rubber.setLayoutX(screenWidth * 0.4 / 8 - tileSize / 2);
        this.rubber.setLayoutY(screenHeight - hBoxSize * 2 / 4 - tileSize / 2);
        

        this.waypoint.setLayoutX(- 200);
        this.waypoint.setLayoutY(- 200);
        this.key.setLayoutX(-200);
        this.key.setLayoutY(-200);

        this.hBox.setLayoutY(screenHeight - hBoxSize);
        this.buttonCreate.setLayoutX(screenWidth * 7.6 / 8 - tileSize / 2);
        this.buttonCreate.setLayoutY(screenHeight - hBoxSize * 2 / 4 - buttonCreate.getHeight() / 2);
    }

    public void setImageViewsGone() {
        this.imageDragged = emtyImageView;
        this.dragging = false;

        this.tile.setLayoutX(-200);
        this.tile.setLayoutY(-200);
        this.tileIron.setLayoutX(-200);
        this.tileIron.setLayoutY(-200);
        this.street.setLayoutX(-200);
        this.street.setLayoutY(-200);
        this.streetCurve.setLayoutX(-200);
        this.streetCurve.setLayoutY(-200);
        this.crossing.setLayoutX(-200);
        this.crossing.setLayoutY(-200);
        this.crossing3.setLayoutX(-200);
        this.crossing3.setLayoutY(-200);
        this.police.setLayoutX(-200);
        this.police.setLayoutY(-200);

        this.energyDrink.setLayoutX(-200);
        this.energyDrink.setLayoutY(-200);
        this.coat.setLayoutX(-200);
        this.coat.setLayoutY(-200);
        this.spawnPoint.setLayoutX(-200);
        this.spawnPoint.setLayoutY(-200);
        this.gate.setLayoutX(-200);
        this.gate.setLayoutY(-200);
        this.finish.setLayoutX(-200);
        this.finish.setLayoutY(-200);
        this.itemToCollectWeed.setLayoutX(-200);
        this.itemToCollectWeed.setLayoutY(-200);
        this.itemToCollectKokain.setLayoutX(-200);
        this.itemToCollectKokain.setLayoutY(-200);
        this.rubber.setLayoutX(-200);
        this.rubber.setLayoutY(-200);

        this.waypoint.setLayoutX(-200);
        this.waypoint.setLayoutY(-200);
        this.key.setLayoutX(-200);
        this.key.setLayoutY(-200);

        this.hBox.setLayoutY(screenHeight);
        this.buttonCreate.setLayoutX(-200);
        this.buttonCreate.setLayoutY(-200);
    }

    private void handleRubberPressed(MouseEvent event) {
        // Delete Tile
        int x = ((int) event.getSceneX() - tileSize / 2) % 50;
        int y = ((int) event.getSceneY() - tileSize / 2) % 50;
        x = (int) event.getSceneX() - tileSize / 2 - (x < tileSize / 2 ? x : x - tileSize);
        y = (int) event.getSceneY() - tileSize / 2 - (y < tileSize / 2 ? y : y - tileSize);
        for(Tile tile : tiles) {
            if(x == tile.getX() && y == tile.getY()) {
                this.rootPane.getChildren().remove(tile.getImageView());
                this.tiles.remove(tile);
                break;
            }
        }

        x = (int) event.getSceneX();
        y = (int) event.getSceneY();
        // Delete Policeman
        for(Policeman policeman : policemans) {
            if(x >= policeman.getX() && x <= policeman.getX() + policeman.getImageView().getFitWidth() && y >= policeman.getY() && y <= policeman.getY() + policeman.getImageView().getFitHeight()) {
                for(Waypoint waypoint : policeman.getWaypoints()) {
                    this.rootPane.getChildren().remove(waypoint.getImageView());
                }
                this.rootPane.getChildren().remove(policeman.getImageView());
                this.policemans.remove(policeman);
                break;
            }
        }
        // Delete Item
        for(Item item : items) {
            if(x >= item.getX() && x <= item.getX() + item.getImageView().getFitWidth() && y >= item.getY() && y <= item.getY() + item.getImageView().getFitHeight()) {
                this.rootPane.getChildren().remove(item.getImageView());
                this.items.remove(item);
                break;
            }
        }
        // Delete Spawnpoint
        if(player != null) {
            if(x >= player.getImage().getX() && x <= player.getImage().getX() + player.getImage().getFitWidth() && y >= player.getImage().getY() && y <= player.getImage().getY() + player.getImage().getFitHeight()) {
                this.rootPane.getChildren().remove(player.getImage());
                this.player = null;
                this.spawnPointSet = false;
                this.spawnPoint.setOpacity(1);
            }
        }
        x = ((int) event.getSceneX() - tileSize) % 100;
        y = ((int) event.getSceneY() - tileSize / 2) % 50;
        x = (int) (event.getSceneX() - tileSize - (x < tileSize ? x : x - tileSize * 2));
        y = (int) (event.getSceneY() - tileSize / 2 - (y < tileSize / 2 ? y : y - tileSize));
        // Delete Gate
        for(int i = 0; i < gates.size(); i++) {
            if((x == gates.get(i).getX() || x == gates.get(i).getX() + tileSize || x == gates.get(i).getX() - tileSize) && y == gates.get(i).getY()) {
                this.rootPane.getChildren().remove(keys.get(i).getImageView());
                this.rootPane.getChildren().remove(gates.get(i).getImageView());
                this.gates.remove(i);
                this.keys.remove(i);
                break;
            }
        }

        x = (int) event.getSceneX();
        y = (int) event.getSceneY();
        // Delete ItemToCollect
        for(Item itemToCollect : itemsToCollect) {
            if(x >= itemToCollect.getX() && x <= itemToCollect.getX() + itemToCollect.getImageView().getFitWidth() && y >= itemToCollect.getY() && y <= itemToCollect.getY() + itemToCollect.getImageView().getFitHeight()) {
                this.rootPane.getChildren().remove(itemToCollect.getImageView());
                this.itemsToCollect.remove(itemToCollect);
                break;
            }
        }
        
        // Delete Finish
        if(goalFinish != null) {
            if(x >= goalFinish.getImageView().getX() && x <= goalFinish.getImageView().getX() + goalFinish.getWidth() && y >= goalFinish.getImageView().getY() && y <= goalFinish.getImageView().getY() + goalFinish.getHeight()) {
                this.rootPane.getChildren().remove(goalFinish.getImageView());
                this.goalFinish = null;
                this.goalFinishSet = false;
                this.finish.setOpacity(1);
            }
        }
    }

    private void handelKeyInput(KeyEvent event) {
        if(imageDragged == street || imageDragged == streetCurve || imageDragged == crossing || imageDragged == crossing3) {
            if(event.getCode() == KeyCode.Q) {
                this.imageDragged.setRotate(this.imageDragged.getRotate() - 90);
            } else if(event.getCode() == KeyCode.E) {
                this.imageDragged.setRotate(this.imageDragged.getRotate() + 90);
            } else if(event.getCode() == KeyCode.F) {
                this.showVboxInfo = !this.showVboxInfo;
                
            }
        }
        if(event.getCode() == KeyCode.ENTER) {
            this.showVboxInfo = !this.showVboxInfo;
            this.vBoxInfo.setVisible(showVboxInfo);
        }
    }

    private void handleMousePressed(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY && isKey()) {
            setImageViews();
            this.rootPane.getChildren().remove(gates.getLast().getImageView());
            this.gates.removeLast();
        } else if(event.getButton() == MouseButton.SECONDARY) {
            setImageViews();
        } else if(isWithinBounds()) {
            placeObjekt(event);
        }
    }

    private void handleMouseMovement(MouseEvent event) {
        if(dragging) {
            followMouse(event);
        }
    }

    private void placeObjekt(MouseEvent event) {
        if(imageDragged == tile) {
            addTile("copperRooftop", true);
        } else if(imageDragged == tileIron) {
            addTile("ironRooftop", true);
        } else if(imageDragged == street) {
            addTile("streetUp", false);
        } else if(imageDragged == streetCurve) {
            addTile("streetCurve", false);
        } else if(imageDragged == crossing) {
            addTile("crossing", false);
        } else if(imageDragged == crossing3) {
            addTile("crossing3", false);
        } else if(imageDragged == police) {
            addPoliceman(event);
        } else if(imageDragged == waypoint) {
            addWayPoint(event);
        } else if(imageDragged == energyDrink) {
            addEnergyDrink(event);
        } else if(imageDragged == coat) {
            addCoat(event);
        } else if(imageDragged == spawnPoint) {
            addSpawnPoint(event);
        } else if(imageDragged == gate) {
            addGate(event);
        } else if(imageDragged == key) {
            addKey(event);
        } else if(imageDragged == itemToCollectWeed) {
            addItemToCollect(event, "grasItem");
        } else if(imageDragged == itemToCollectKokain) {
            addItemToCollect(event, "sussyCocain");
        } else if(imageDragged == finish) {
            addFinish(event);
        }
    }

    private void addFinish(MouseEvent event) {
        this.goalFinish = new Finish((int) finish.getLayoutX(), (int) finish.getLayoutY(), tileSize, tileSize, this.goal, "finishBlack");
        this.rootPane.getChildren().add(tiles.size(), goalFinish.getImageView());
        setImageViews();
        this.goalFinishSet = true;
        this.finish.setOpacity(0.2);
    }

    private void addItemToCollect(MouseEvent event, String name) {
        ItemToCollect itemToCollect = new ItemToCollect(name, (int) event.getSceneX(), (int) event.getSceneY(), name, true);
        this.itemsToCollect.add(itemToCollect);
        this.rootPane.getChildren().add(tiles.size(), itemToCollect.getImageView());
    }

    private void addGate(MouseEvent event) {
        Gate newGate = new Gate((int) gate.getLayoutX(), (int) gate.getLayoutY(), tileSize * 2, tileSize, new ImageView(new Image(Graphics.getGraphicUrl("gateClosed"))));
        this.gates.add(newGate);
        this.rootPane.getChildren().add(tiles.size(), newGate.getImageView());
        this.imageDragged = key;
        this.dragging = true;
        followMouse(event);
    }

    private void addKey(MouseEvent event) {
        Key newKey = new Key("key", (int) event.getSceneX(), (int) event.getSceneY(), "key", false);
        this.keys.add(newKey);
        this.rootPane.getChildren().add(tiles.size(), newKey.getImageView());
        setImageViews();
    }

    private void addPoliceman(MouseEvent event) {
        Policeman policeman = new Policeman(
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_STANDART_SPEED")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")),
            (int) event.getSceneX(),
            (int) event.getSceneY(),
            "policeman"
        );
        policeman.setX(policeman.getX());
        policeman.setY(policeman.getY());
        this.policemans.add(policeman);
        addWayPoint(event);
        this.rootPane.getChildren().add(tiles.size(), policeman.getImageView());
        this.imageDragged = waypoint;
        this.dragging = true;
        followMouse(event);
    }

    private void addSpawnPoint(MouseEvent event) {
        this.player = new Player(
            Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")),
            Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_COLLECT_RANGE")),
            new Rectangle(50, 50),
            (int) event.getSceneX(),
            (int) event.getSceneY()
        );
        this.rootPane.getChildren().add(tiles.size(), player.getImage());
        setImageViews();
        this.spawnPointSet = true;
        this.spawnPoint.setOpacity(0.2);
    }

    private void addCoat(MouseEvent event) {
        Item coat = new Coat("coat", (int) event.getSceneX(), (int) event.getSceneY(), "coat", false);
        this.items.add(coat);
        this.rootPane.getChildren().add(tiles.size(), coat.getImageView());
    }

    private void addEnergyDrink(MouseEvent event) {
        EnergyDrink energyDrink = new EnergyDrink("energyDrink", (int) event.getSceneX(), (int) event.getSceneY(), "energyDrink", false);
        this.items.add(energyDrink);
        this.rootPane.getChildren().add(tiles.size(), energyDrink.getImageView());
    }

    private void addWayPoint(MouseEvent event) {
        Waypoint waypoint = new Waypoint((int) event.getSceneX(), (int) event.getSceneY(), true, "flag", policemans.size());
        this.policemans.getLast().addWaypoint(waypoint);
        this.rootPane.getChildren().add(tiles.size(), policemans.getLast().getWaypoints().getLast().getImageView());
    }

    private void addTile(String name, boolean solid) {
        for(Tile tile : tiles) { //check if there is alredy a  tile on the same spot
            if(Math.abs(imageDragged.getLayoutX() - tile.getX()) < 5 && Math.abs(imageDragged.getLayoutY() - tile.getY()) < 5) {
                return;
            }
        }
        Tile newTile = new Tile(solid, (int) imageDragged.getLayoutX(), (int) imageDragged.getLayoutY(), tileSize, tileSize, name, imageDragged.getRotate());
        this.tiles.add(newTile);
        this.rootPane.getChildren().add(0, newTile.getImageView());
    }

    public boolean isWithinBounds() {

        return imageDragged.getLayoutX() >= 0 && imageDragged.getLayoutX() <= screenWidth && imageDragged.getLayoutY() >= 0 && imageDragged.getLayoutY() <= hBox.getLayoutY();
    }

    private boolean isTile() {
        return imageDragged == tile || imageDragged == tileIron || imageDragged == street || imageDragged == streetCurve || imageDragged == crossing || imageDragged == crossing3;
    }


    private void followMouse(MouseEvent event) {
        if(isTile() || imageDragged == finish || imageDragged == gate) {
            int x = ((int) event.getSceneX() - tileSize / 2) % 50;
            int y = ((int) event.getSceneY() - tileSize / 2) % 50;
            imageDragged.setLayoutX(event.getSceneX() - tileSize / 2 - (x < tileSize / 2 ? x : x - tileSize));
            imageDragged.setLayoutY(event.getSceneY() - tileSize / 2 - (y < tileSize / 2 ? y : y - tileSize));
        } else {
            imageDragged.setLayoutX(event.getSceneX() - tileSize / 2);
            imageDragged.setLayoutY(event.getSceneY() - tileSize / 2);
        }
    }

    private boolean isKey() {
        return imageDragged == key;
    }

    private void startDragging(ImageView imageView, MouseEvent event) {
        if(dragging || (imageView == spawnPoint && spawnPointSet) || (imageView == finish && goalFinishSet)) {
        } else {
            
            setImageViewsGone();
            this.dragging = true;
            this.imageDragged = imageView;
            this.rubber.toFront();
            handleMouseMovement(event);
        }
    }

}