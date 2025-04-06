package models.Screens;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class StartScreen {

    private Button buttonLevelSelectionStory;
    private Button buttonLevelSelectionMyMaps;
    private Button buttonOptions;
    private Button buttonQuit;
    private Button buttonStatistics;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private VBox vBox;
    private ImageView backgroundImageView;

    public StartScreen(Stage stage) {
        this.vBox = new VBox(40);
        this.buttonLevelSelectionStory = new Button(Texts.getTextByName("buttonLevelSelectionStory").getTextInLanguage());
        this.buttonLevelSelectionMyMaps = new Button(Texts.getTextByName("buttonLevelSelectionMyMaps").getTextInLanguage());
        this.buttonOptions = new Button(Texts.getTextByName("buttonOptions").getTextInLanguage());
        this.buttonQuit = new Button(Texts.getTextByName("buttonQuit").getTextInLanguage());
        this.buttonStatistics = new Button(Texts.getTextByName("statisticsScreenTitle").getTextInLanguage());
        this.stage = stage;
        this.rootPane = new Pane();
        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.buttonLevelSelectionStory.setOnAction(event -> {
            new LevelSelection(stage, ConfigArguments.getConfigArgumentValue("STORY_MAPS_PATH"), true);
        });
        this.buttonLevelSelectionMyMaps.setOnAction(event -> {
            new LevelSelection(stage, ConfigArguments.getConfigArgumentValue("MY_MAPS_PATH"), true);
        });
        this.buttonOptions.setOnAction(event -> {
            new Options(stage);
        });
        this.buttonQuit.setOnAction(event -> {
            stage.close();
        });

        this.buttonStatistics.setOnAction(event -> {
            new StatisticsScreen(stage);
        });
        
        this.vBox.getChildren().addAll(buttonLevelSelectionStory, buttonLevelSelectionMyMaps, buttonOptions, buttonStatistics, buttonQuit);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().addAll(backgroundImageView, vBox);
        this.stage.setResizable(false);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("StartScreen").getTextInLanguage());
        this.stage.show();
        double width = vBox.getWidth();
        double height = vBox.getHeight();
        this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        this.vBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height) / 2));
    }
    
}