package models.Screens;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import levels.Level;
import utils.config.ConfigArguments;

public class LevelSelection {
    
    private ArrayList<Level> levels;
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private ArrayList<Button> buttons;
    private FlowPane flowPane;

    public LevelSelection(ArrayList<Level> levels, Stage stage) {
        this.flowPane = new FlowPane();
        this.levels = levels;
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
        flowPane.setStyle("-fx-background-color: lightblue;");

        for(Level level : levels) {
            Button button = new Button(level.getMapName());
            button.setOnAction(event -> {
                level.start();
            });

            if(!level.getAvailable()) {
                button.setDisable(true);
            }
    
            this.buttons.add(button);
            this.flowPane.getChildren().add(button);
        }
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

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public ArrayList<Level> getLevels() {
        return levels;
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
