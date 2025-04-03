package models.Screens;

import java.util.ArrayList;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import levels.Level;
import utils.config.ConfigArguments;
import utils.mapConfig.MapReader;
import utils.mapConfig.MapWriter;
import utils.statistics.Statistics;

public class LevelSelection {
    private static ArrayList<String> mapNames;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private ArrayList<Button> buttons;
    private Button buttonExit;
    private FlowPane flowPane;
    private static ArrayList<Boolean> levelDisabled = new ArrayList<>();
    private ImageView backgroundImageView;

    public LevelSelection(Stage stage, String mapsPath) {
        this.rootPane = new Pane();
        LevelSelection.mapNames = MapReader.readMapNames(mapsPath);
        this.flowPane = new FlowPane();
        this.stage = stage;
        this.buttons = new ArrayList<>();
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.backgroundImageView.setEffect(new GaussianBlur(30));
        this.flowPane.setPrefWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) * 5 / 6);
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
            if(i > Integer.parseInt(Statistics.getStatisticValue("LAST_LEVEL_INDEX"))) {
                button.setDisable(true);
            }
            this.buttons.add(button);
            this.flowPane.getChildren().addAll(button);
        }
        this.buttonExit.setOnAction(event -> {
            new StartScreen(stage);
        });

        this.flowPane.setAlignment(Pos.CENTER);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.rootPane.getChildren().addAll(backgroundImageView, flowPane, buttonExit);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("LevelSelectionScreen").getTextInLanguage());
        this.stage.show();
        double width = this.buttonExit.getWidth();
        double height = this.flowPane.getLayoutY() + this.flowPane.getHeight();
        this.buttonExit.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        this.buttonExit.setLayoutY(height + (Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height - this.buttonExit.getHeight()) / 2 );
        width = flowPane.getWidth();
        this.flowPane.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        this.flowPane.setLayoutY(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) * 1 / 6);
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
