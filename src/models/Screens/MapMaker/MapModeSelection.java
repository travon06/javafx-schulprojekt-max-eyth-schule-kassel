package models.Screens.MapMaker;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import models.Screens.LevelSelection;
import models.Screens.MapMakerNew;
import utils.config.ConfigArguments;

public class MapModeSelection {
    private Scene scene;
    private Pane rootPane;
    private Button collectItemsButton;
    private Button surviveButton;
    private HBox mapModebuttonHBox;
    private ImageView backgroundImageView;
    private final int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
    private final int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    public MapModeSelection(Stage stage) {
        this.rootPane = new Pane();
        this.scene = new Scene(
            rootPane,
            screenWidth,
            screenHeight
        );

        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.backgroundImageView.setEffect(new GaussianBlur(30));

        this.mapModebuttonHBox = new HBox(50);
        // collect items button
        this.collectItemsButton = new Button(Texts.getTextByName("collectItemsButtonMapModeSelection").getTextInLanguage());
        this.collectItemsButton.setOnAction(event -> {
            new MapMakerNew(stage, "COLLECT_ITEMS");
        });

        // survive button
        this.surviveButton = new Button(Texts.getTextByName("surviveButtonMapModeSelection").getTextInLanguage());
        this.surviveButton.setOnAction(event -> {
            new MapMakerNew(stage, "SURVIVE");

        });
        this.scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                new LevelSelection(stage, ConfigArguments.getConfigArgumentValue("MY_MAPS_PATH"));
            }
        });

        this.mapModebuttonHBox.getChildren().addAll(collectItemsButton, surviveButton);
        this.mapModebuttonHBox.setAlignment(Pos.CENTER);

        Platform.runLater(() -> {
            mapModebuttonHBox.setLayoutX((screenWidth  / 2) - mapModebuttonHBox.getWidth() / 2);
            mapModebuttonHBox.setLayoutY((screenHeight / 2) - mapModebuttonHBox.getHeight() / 2);
        });
        this.rootPane.getChildren().addAll(backgroundImageView, mapModebuttonHBox);

        this.rootPane.getStylesheets().add(getClass().getResource("../../../style/screens.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(Texts.getTextByName("mapModeSelectionTitle").getTextInLanguage());
        stage.show();
    }   
}
