package models.Screens;

import java.util.ArrayList;

import graphics.Graphic;
import graphics.Graphics;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        this.buBox = new VBox(20);
        buttonLevelSelection = new Button(Texts.getTextByName("buttonLevelSelection").getTextInLanguage());
        buttonOptions = new Button(Texts.getTextByName("buttonOptions").getTextInLanguage());
        buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
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
        this.buBox.getChildren().addAll(buttonLevelSelection, buttonOptions, buttonExit);
        this.rootPane.getChildren().addAll(buBox);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§§§§§§§§");
        this.stage.show();
    }
    
}
