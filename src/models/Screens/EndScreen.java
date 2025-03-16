package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class EndScreen {
    private Stage stage;
    private Pane rootPane;
    private Scene scene;
    private Label messageLabel;

    public EndScreen(Stage stage) {
        this.stage = stage;
        this.rootPane = new Pane();
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")),
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        this.messageLabel = new Label(Texts.getTextByName("endScreenMessageLabel").getTextInLanguage());

        this.rootPane.getChildren().addAll(messageLabel);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("endScreen").getTextInLanguage());
        this.stage.show();
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
    
    //#endregion
}
