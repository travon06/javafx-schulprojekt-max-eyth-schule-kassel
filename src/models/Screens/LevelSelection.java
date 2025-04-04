package models.Screens;

import java.util.ArrayList;

import graphics.Graphics;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import levels.Level;
import models.Screens.MapMaker.MapModeSelection;
import utils.config.ConfigArguments;
import utils.mapConfig.MapReader;
import utils.statistics.Statistics;

public class LevelSelection {
    private static ArrayList<String> mapNames;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private ArrayList<Button> buttons;
    private Button buttonExit;
    private Button buttonMapMaker;
    private FlowPane flowPane;
    private static ArrayList<Boolean> levelDisabled = new ArrayList<>();
    private ImageView backgroundImageView;
    private static final int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
    private static final int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));

    public LevelSelection(Stage stage, String mapsPath) {
        this.rootPane = new Pane();
        LevelSelection.mapNames = MapReader.readMapNames(mapsPath);
        this.flowPane = new FlowPane();
        this.stage = stage;
        this.buttons = new ArrayList<>();
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.buttonMapMaker = new Button(Texts.getTextByName("mapMaker").getTextInLanguage());
        this.scene = new Scene(
            this.rootPane,
            screenWidth,
            screenHeight
        );

        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(screenWidth);
        this.backgroundImageView.setFitHeight(screenHeight);
        this.backgroundImageView.setEffect(new GaussianBlur(30));
        this.flowPane.setPrefWidth(screenWidth * 5 / 6);
        this.flowPane.setHgap(20);
        this.flowPane.setVgap(30);
        for(int i = 0; i < LevelSelection.mapNames.size(); i++) {
            Button button = new Button(mapNames.get(i));
            final int index = i;
            button.setOnAction(event -> {
                Level level = new Level(stage, mapNames.get(index), mapsPath, MapReader.getNextLevel(mapNames.get(index), mapsPath));
                level.start();


            });
            if(!Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("DEVELOPMENT_MODE")))
            if(i > Integer.parseInt(Statistics.getStatisticValue("LAST_LEVEL_INDEX")) && !mapsPath.equals(ConfigArguments.getConfigArgumentValue("MY_MAPS_PATH"))) {
                button.setDisable(true);
            }
            this.buttons.add(button);
            this.flowPane.getChildren().addAll(button);
        }

        this.buttonMapMaker.setOnAction(event -> {
            new MapModeSelection(stage);
        });
        
        this.buttonExit.setOnAction(event -> {
            new StartScreen(stage);
        });

        
        this.scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                new StartScreen(stage);
            }
        });

        this.flowPane.setAlignment(Pos.CENTER);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.rootPane.getChildren().addAll(backgroundImageView, flowPane, buttonExit);
        if(mapsPath.equals(ConfigArguments.getConfigArgumentValue("MY_MAPS_PATH"))) {
            this.rootPane.getChildren().add(buttonMapMaker);
        }
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("LevelSelectionScreen").getTextInLanguage());
        this.stage.show();
        this.buttonMapMaker.setLayoutX((screenWidth - this.buttonMapMaker.getWidth()) / 2);
        this.buttonMapMaker.setLayoutY(screenHeight * 3 / 6);
        double width = this.buttonExit.getWidth();
        double height = this.flowPane.getLayoutY() + this.flowPane.getHeight();
        this.buttonExit.setLayoutX((screenWidth - width) / 2);
        this.buttonExit.setLayoutY(screenHeight * 5 / 6);
        width = flowPane.getWidth();
        this.flowPane.setLayoutX((screenWidth - width) / 2);
        this.flowPane.setLayoutY(screenHeight * 1 / 6);
    }

    //#region getter & setter
    public static void disableButton(boolean disable, int index) {
        while(levelDisabled.size() <= index) {
            levelDisabled.add(true);
        }
        levelDisabled.set(index, disable);
    }
    public static boolean getDisabledButton(int index) {
        while(levelDisabled.size() <= index) {
            levelDisabled.add(true);
        }
        return levelDisabled.get(index);
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public static ArrayList<String> getMapNames() {
        return LevelSelection.mapNames;
    }

    public static ArrayList<Boolean> getLevelDisabled() {
        return levelDisabled;
    }



    //#endregion

}
