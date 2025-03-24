package models.Screens;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import models.tiles.Tile;
import utils.config.ConfigArguments;

public class MapMaker {
    private static final int OBJEKTE_HOEHE = 300;
    private final Scene scene;
    private final Pane rootPane;
    private final HBox hBox;
    private final ArrayList<Tile> tilesArrayList = new ArrayList<>();
    private boolean dragging = false;
    private boolean isMousePressed = false;
    private ImageView imageGettingDragged = new ImageView();
    private final Tile tile;
    private final Tile tileIron;
    private final Tile rubber;

    AnimationTimer timer;

    public MapMaker(Stage stage) {
        this.rootPane = new Pane();
        int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        this.scene = new Scene(this.rootPane, screenWidth, screenHeight + OBJEKTE_HOEHE);

        this.hBox = new HBox(20);
        this.hBox.setPrefSize(screenWidth, OBJEKTE_HOEHE);
        this.hBox.setLayoutY(screenHeight);
        this.hBox.setStyle("-fx-background-color:grey");

        this.tile = createTile("copperRooftop", screenWidth * 1 / 10);
        this.tileIron = createTile("ironRooftop", screenWidth * 2 / 10);
        this.rubber = createTile("key", screenWidth * 3 / 10);

        setupMouseEvents();
        rootPane.getChildren().addAll(hBox, tile.getImageView(), tileIron.getImageView(), rubber.getImageView());
        stage.setScene(scene);
        stage.setTitle(Texts.getTextByName("mapMaker").getTextInLanguage());
        stage.show();

        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println(isMousePressed);
            }
        };
        timer.start();  
    }

    private Tile createTile(String type, int xPosition) {
        Tile tile = new Tile(false, 0, 0, 50, 50, type);
        tile.setX(xPosition - 25);
        tile.setY(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) + OBJEKTE_HOEHE / 2 - 25);
        tile.getImageView().setOnMousePressed(event -> startDragging(tile));
        return tile;
    }

    private void setupMouseEvents() {
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY && dragging) {
                resetTiles();
                dragging = false;
            } else {
                isMousePressed = true;
                handleMouseMovement(event);
            }
        });
        scene.setOnMouseReleased(event -> isMousePressed = false);
        scene.setOnMouseMoved(this::handleMouseMovement);
        scene.setOnMouseDragged(this::handleMouseMovement);
    }

    private void startDragging(Tile tile) {
        dragging = true;
        imageGettingDragged = tile.getImageView();
    }

    private void resetTiles() {
        dragging = false;
        tile.setX(tile.getX());
        tile.setY(tile.getY());
        tileIron.setX(tileIron.getX());
        tileIron.setY(tileIron.getY());
        rubber.setX(rubber.getX());
        rubber.setY(rubber.getY());
    }

    private void handleMouseMovement(MouseEvent event) {
        if (dragging && isValidDrag()) {
            snapToGrid(event);
        }
        if (isMousePressed && isWithinBounds()) {
            processTilePlacement();
        }
    }

    private boolean isValidDrag() {
        return imageGettingDragged.getImage() == tile.getImageView().getImage() ||
               imageGettingDragged.getImage() == tileIron.getImageView().getImage() ||
               imageGettingDragged.getImage() == rubber.getImageView().getImage();
    }

    private void snapToGrid(MouseEvent event) {
        int x = ((int) event.getSceneX() - 25) % 50;
        int y = ((int) event.getSceneY() - 25) % 50;
        
        imageGettingDragged.setX(event.getSceneX() - 25 - (x < 25 ? x : x - 50));
        imageGettingDragged.setY(event.getSceneY() - 25 - (y < 25 ? y : y - 50));
    }

    private boolean isWithinBounds() {
        int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        return imageGettingDragged.getY() < screenHeight && imageGettingDragged.getX() >= 0 &&
               imageGettingDragged.getY() >= 0 && imageGettingDragged.getX() < screenWidth;
    }

    private void processTilePlacement() {
        if (imageGettingDragged.getImage() == rubber.getImageView().getImage()) {
            tilesArrayList.removeIf(tile -> {
                boolean match = tile.getX() == imageGettingDragged.getX() && tile.getY() == imageGettingDragged.getY();
                if (match) rootPane.getChildren().remove(tile.getImageView());
                return match;
            });
        } else {
            if (tilesArrayList.stream().noneMatch(tile -> tile.getX() == imageGettingDragged.getX() && tile.getY() == imageGettingDragged.getY())) {
                addTile(imageGettingDragged);
            }
        }
    }

    private void addTile(ImageView imageView) {
        String type = (imageView.getImage() == tile.getImageView().getImage()) ? "copperRooftop" : "ironRooftop";
        Tile newTile = new Tile(false, (int) imageGettingDragged.getX(), (int) imageGettingDragged.getY(), 50, 50, type);
        tilesArrayList.add(newTile);
        rootPane.getChildren().add(0, newTile.getImageView());
    }
}
