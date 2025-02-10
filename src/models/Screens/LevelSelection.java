package models.Screens;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import levels.Level;
import utils.config.ConfigArguments;
import utils.mapConfig.MapReader;

public class LevelSelection {
    private ArrayList<String> mapNames;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private ArrayList<Button> buttons;
    private FlowPane flowPane;

    public LevelSelection(Stage stage) {
        this.mapNames = MapReader.readMapNames();
        this.flowPane = new FlowPane();
        this.stage = stage;
        this.rootPane = new Pane();
        this.buttons = new ArrayList<>();
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        Platform.runLater(() -> {
            double width = flowPane.getWidth();
            this.flowPane.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        });
        this.flowPane.setMaxWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) * 2 / 3);
        this.flowPane.setHgap(10);
        this.flowPane.setVgap(10);

        for(String mapName : mapNames) {
            Button button = new Button(mapName);
            button.setOnAction(event -> {
                Level level = new Level(stage, mapName);
                level.start();
            });
    
            this.buttons.add(button);
            this.flowPane.getChildren().add(button);
        }
        this.flowPane.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().add(flowPane);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§§§§§§§§");
        this.stage.show();
        
    }

    //#region getter & setter

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

    //#endregion

}
