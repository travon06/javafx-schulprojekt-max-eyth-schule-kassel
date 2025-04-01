package models.Screens;

import java.util.ArrayList;

import graphics.Graphics;
import items.Coat;
import items.EnergyDrink;
import items.Item;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.Waypoint;
import utils.config.ConfigArguments;

public class MapMaker {
    private Scene scene;
    private Pane rootPane;
    private HBox hBox;
    private static final int hBoxSize = 150;
    private ImageView tile;
    private ImageView tileIron;
    private ImageView police;
    private ImageView waypoint;
    private ImageView energyDrink;
    private ImageView coat;
    private static final int tileSize = 50;
    private static final int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
    private static final int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    private ImageView imageDragged = new ImageView();
    private boolean dragging;
    private ArrayList<Tile> tilesList;
    private ArrayList<Policeman> policemans;
    private ArrayList<Item> items;

    public MapMaker(Stage stage) {
        this.rootPane = new Pane();
        this.scene = new Scene(this.rootPane, screenWidth, screenHeight + hBoxSize);

        this.hBox = new HBox();
        this.hBox.setPrefSize(screenWidth, hBoxSize);
        this.hBox.setLayoutY(screenHeight);
        this.hBox.setStyle("-fx-background-color:grey");

        this.dragging = false;
        this.tilesList = new ArrayList<>();
        this.policemans = new ArrayList<>();
        this.items = new ArrayList<>();

        this.tile = new ImageView(new Image(Graphics.getGraphicUrl("copperRooftop")));
        this.tile.setFitWidth(tileSize);
        this.tile.setFitHeight(tileSize);
        this.tileIron = new ImageView(new Image(Graphics.getGraphicUrl("ironRooftop")));
        this.tileIron.setFitWidth(tileSize);
        this.tileIron.setFitHeight(tileSize);

        this.police = new ImageView(new Image((Graphics.getGraphicUrl("policeman"))));
        this.police.setFitWidth(tileSize);
        this.police.setFitHeight(tileSize);
        this.waypoint = new ImageView(new Image((Graphics.getGraphicUrl("key"))));
        this.waypoint.setFitWidth(tileSize);
        this.waypoint.setFitHeight(tileSize);

        this.energyDrink = new ImageView(new Image((Graphics.getGraphicUrl("energyDrink"))));
        this.energyDrink.setFitWidth(tileSize);
        this.energyDrink.setFitHeight(tileSize);
        this.coat = new ImageView(new Image(Graphics.getGraphicUrl("coat")));
        this.coat.setFitWidth(hBoxSize);
        this.coat.setFitHeight(hBoxSize);


        setImageViews();
        setUpMouseMovements();

        rootPane.getChildren().addAll(hBox, tile, tileIron, police, waypoint, energyDrink, coat);
        stage.setScene(scene);
        stage.setTitle(Texts.getTextByName("mapMaker").getTextInLanguage());
        stage.show();
    }

    private void setImageViews() {
        this.tile.setLayoutX(screenWidth * 1 / 10);
        this.tile.setLayoutY(screenHeight + hBoxSize / 2 - tileSize / 2);
        this.tileIron.setLayoutX(screenWidth * 2 / 10);
        this.tileIron.setLayoutY(screenHeight + hBoxSize / 2 - tileSize / 2);
        this.police.setLayoutX(screenWidth * 3 / 10);
        this.police.setLayoutY(screenHeight + hBoxSize / 2 - tileSize / 2);
        this.waypoint.setLayoutX(- 200);
        this.waypoint.setLayoutY(- 200);
        this.energyDrink.setLayoutX(screenWidth * 4 / 10);
        this.energyDrink.setLayoutY(screenHeight + hBoxSize / 2 - tileSize / 2);
        this.coat.setLayoutX(screenWidth * 5 / 10);
        this.coat.setLayoutY(screenHeight + hBoxSize / 2 - tileSize / 2);
    }

    private void setUpMouseMovements() {
        this.tile.setOnMousePressed(event -> startDragging(tile));
        this.tileIron.setOnMousePressed(event -> startDragging(tileIron));
        this.police.setOnMousePressed(event -> startDragging(police));
        this.energyDrink.setOnMousePressed(event -> startDragging(energyDrink));
        this.coat.setOnMousePressed(event -> startDragging(coat));

        scene.setOnMouseMoved(event -> handleMouseMovement(event));
        scene.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                dragging = false;
                setImageViews();
            }else if(isWithinBounds()) placeObjekt(event);
        });
        scene.setOnMouseDragged(event -> {
            handleMouseMovement(event);
            if(isWithinBounds() && isTile()) placeObjekt(event);
        });
    }

    private void handleMouseMovement(MouseEvent event) {
        if(dragging) {
            followMouse(event);
        }
    }

    private void placeObjekt(MouseEvent event) {
        if(imageDragged == tile) {
            addTile("copperRooftop");
        } else if(imageDragged == tileIron) {
            addTile("ironRooftop");
        } else if(imageDragged == police) {
            addPoliceman(event);
        } else if(imageDragged == energyDrink) {
            addEnergyDrink(event);
        } else if(imageDragged == coat) {
            addCoat(event);
        }
    }

    private void addCoat(MouseEvent event) {
        Coat coat = new Coat(Texts.getTextByName("coat").getTextInLanguage(), (int) event.getSceneX(), (int) event.getSceneY(), "coat", false);
        this.items.add(coat);
        this.rootPane.getChildren().add(0, coat.getImageView());
    }

    private void addEnergyDrink(MouseEvent event) {
        EnergyDrink energyDrink = new EnergyDrink(Texts.getTextByName("energyDrink").getTextInLanguage(), (int) event.getSceneX(), (int) event.getSceneY(), "energyDrink", false);
        this.items.add(energyDrink);
        this.rootPane.getChildren().add(0, energyDrink.getImageView());
    }
    private void addPoliceman(MouseEvent event) {
        Policeman policeman = new Policeman(
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_STANDART_SPEED")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")),
            (int) event.getSceneX(),
            (int) event.getSceneY(),
            "policeman"
        );
        policeman.setX(event.getSceneX());
        policeman.setY(event.getSceneY());
        this.policemans.add(policeman);
        this.rootPane.getChildren().add(0, policeman.getImageView());
        setImageViews();
        this.imageDragged = waypoint;
        followMouse(event);
    }

    private void addTile(String name) {
        for(Tile tile : tilesList) {
            if(imageDragged.getLayoutX() == tile.getX() && imageDragged.getLayoutY() == tile.getY()) {
                return;
            }
        }
        Tile newTile = new Tile(true, (int) imageDragged.getLayoutX(), (int) imageDragged.getLayoutY(), tileSize, tileSize, name);
        this.tilesList.add(newTile);
        this.rootPane.getChildren().add(0, newTile.getImageView());
    }

    public boolean isWithinBounds() {
        return imageDragged.getLayoutY() < screenHeight && imageDragged.getLayoutX() >= 0 &&
               imageDragged.getLayoutY() >= 0 && imageDragged.getLayoutX() < screenWidth;
    }

    private boolean isTile() {
        return imageDragged == tile || imageDragged == tileIron;
    }


    private void followMouse(MouseEvent event) {
        if(isTile()) {
            int x = ((int) event.getSceneX() - tileSize / 2) % 50;
            int y = ((int) event.getSceneY() - tileSize / 2) % 50;
            imageDragged.setLayoutX(event.getSceneX() - tileSize / 2 - (x < tileSize / 2 ? x : x - tileSize));
            imageDragged.setLayoutY(event.getSceneY() - tileSize / 2 - (y < tileSize / 2 ? y : y - tileSize));
        } else {
            imageDragged.setLayoutX(event.getSceneX() - tileSize / 2);
            imageDragged.setLayoutY(event.getSceneY() - tileSize / 2);
        }
    }

    private void startDragging(ImageView imageView) {
        this.dragging = true;
        this.imageDragged = imageView;
    }
    
}
