package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import levels.Level;
import utils.config.ConfigArguments;
import utils.mapConfig.MapReader;
import utils.statistics.Statistics;


public class GameoverScreen {

    private Pane rootPane;
    private Stage stage;
    private Label messageLabel;
    private Button retryButton;
    private Scene scene;

    public GameoverScreen(Stage stage, String mapName, String mapsPath) {

        // update statistics file
        int timesCaught = Integer.parseInt(Statistics.getStatisticValue("TIMES_CAUGHT"));
        
        Statistics.setStatisticValue("TIMES_CAUGHT", String.valueOf(timesCaught + 1));

        this.rootPane = new Pane();
        this.stage = stage;
        this.scene = new Scene(
            rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.messageLabel = new Label();
        this.messageLabel.setId("messageLabel");
        messageLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(messageLabel.widthProperty()).divide(2));
        messageLabel.layoutYProperty().bind(rootPane.heightProperty().subtract(messageLabel.heightProperty()).divide(2).subtract(100));
        this.retryButton = new Button(Texts.getTextByName("gameoverScreenRetryButton").getTextInLanguage());
        this.retryButton.setId("retryButton");
        retryButton.layoutXProperty().bind(rootPane.widthProperty().subtract(retryButton.widthProperty()).divide(2));
        retryButton.layoutYProperty().bind(rootPane.heightProperty().subtract(retryButton.heightProperty()).divide(2));
        this.retryButton.setOnAction(event ->{
            Level level = new Level(stage, mapName, mapsPath, MapReader.getNextLevel(mapName, mapsPath));
            level.start();
        });
        this.scene.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.rootPane.getChildren().addAll(messageLabel, retryButton);
        this.stage.setScene(scene);
    }

    public void setDeathMessage(String message) {
        this.messageLabel.setText(message);
    }

    //#region getter & setter

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setRetryButton(Button retryButton) {
        this.retryButton = retryButton;
    }

    public Button getRetryButton() {
        return retryButton;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    //#endregion
}