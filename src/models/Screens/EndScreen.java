package models.Screens;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class EndScreen {
    private Stage stage;
    private Pane rootPane;
    private Scene scene;
    private Label labelCongratulations;
    private Label labelText;
    private VBox vBox;
    private AnimationTimer animationTimer;
    private boolean timerFinished;
    private FlowPane flowPane;

    public EndScreen(Stage stage) {
        this.timerFinished = false;
        this.stage = stage;
        this.rootPane = new Pane();
        this.flowPane = new FlowPane();
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        this.labelCongratulations = new Label(Texts.getTextByName("labelcongratulations").getTextInLanguage());
        this.labelCongratulations.setId("congratulations");
        this.labelText = new Label("§§§§§§§§§§§§§§§§§§§§");
        this.labelText.setId("text");
        this.vBox = new VBox();
        waitTimer();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(timerFinished) vBox.setLayoutY(vBox.getLayoutY()-0.3);
            }
        };
        this.animationTimer.start();

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                new StartScreen(stage);
            }
        });

        this.flowPane.getChildren().addAll(labelText);
        this.flowPane.setAlignment(Pos.CENTER);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(labelCongratulations, flowPane);
        this.rootPane.getChildren().addAll(vBox);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("endScreen").getTextInLanguage());
        this.stage.show();
        double width = vBox.getWidth();
        double height = vBox.getHeight();
        this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
        this.vBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height) / 2) - 100);
    }

    //#region getter & setter

    public Pane getRootPane() {
        return rootPane;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTimerFinished(boolean b) {
        timerFinished = b;
    }

    //#endregion

    public void waitTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTimerFinished(true);
                timer.cancel();
            }
            
        }, 5000);
    }
}