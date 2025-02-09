package models.Screens;

import java.util.ArrayList;

import graphics.Graphic;
import graphics.Graphics;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Text;
import language.TextReader;
import language.Texts;
import levels.Level;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;

public class StartScreen {

    private Button buttonLevelSelection;
    private Button buttonOptions;
    private Button buttonExit;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private VBox buBox;

    public StartScreen(Stage stage, ArrayList<Level> levels) {
        this.buBox = new VBox(40);
        this.buttonLevelSelection = new Button(Texts.getTextByName("buttonLevelSelection").getTextInLanguage());
        this.buttonOptions = new Button(Texts.getTextByName("buttonOptions").getTextInLanguage());
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.stage = stage;
        this.rootPane = new Pane();
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.buttonLevelSelection.setOnAction(event -> {
            LevelSelection levelSelection = new LevelSelection(levels, stage);
        });
        this.buttonOptions.setOnAction(event -> {
            Options options = new Options(stage);
        });
        this.buttonExit.setOnAction(event -> {
            stage.close();
        });
        
        Platform.runLater(() -> {
            double width = buBox.getWidth();
            this.buBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        });
        Platform.runLater(() -> {
            double height = buBox.getHeight();
            this.buBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height) / 2) - 100);
        });
        
        this.buBox.getChildren().addAll(buttonLevelSelection, buttonOptions, buttonExit);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/startScreen.css").toExternalForm());
        this.rootPane.getChildren().addAll(buBox);
        this.buBox.setAlignment(Pos.CENTER);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§§§§§§§§");
        this.stage.show();
    }
    
}