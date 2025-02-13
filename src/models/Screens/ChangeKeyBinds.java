package models.Screens;

import com.sun.glass.ui.PlatformFactory;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;
import utils.keyboard.Keybindings;

public class ChangeKeyBinds {

    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button buttonWalkUp;
    private Button buttonExit;
    private VBox vBox;
    private String newButtonBind;

    public ChangeKeyBinds(Stage stage) {
        this.buttonWalkUp = new Button(Keybindings.getKeybindingValue("WALK_UP"));
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.rootPane = new Pane();
        this.stage = stage;
        this.vBox = new VBox();
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        
        this.buttonWalkUp.setOnAction(event -> {
            this.buttonWalkUp.setText("");
            changeKeyBind("WALK_UP", buttonWalkUp);
        });

        this.buttonExit.setOnAction(event -> {
            Options options = new Options(stage);
        });

        Platform.runLater(() -> {
            double width = vBox.getWidth();
            this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) * 6 / 9);
        });
        Platform.runLater(() -> {
            double width = this.buttonExit.getWidth();
            double height = this.vBox.getLayoutY() + this.vBox.getHeight();
            this.buttonExit.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
            this.buttonExit.setLayoutY(height + (Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height - this.buttonExit.getHeight()) / 2 );
        });


        this.scene.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.vBox.getChildren().addAll(buttonWalkUp);
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().addAll(vBox, buttonExit);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§§§§§§§§");
        this.stage.show();
    }

    public void changeKeyBind(String keyToChange, Button button) {
        this.stage.getScene().setOnKeyPressed(keyEvent -> {
            this.newButtonBind = keyEvent.getCode().toString();
            Keybindings.setKeybindingValue(keyToChange, newButtonBind);
            this.stage.getScene().setOnKeyPressed(null);
            button.setText(Keybindings.getKeybindingValue("WALK_UP"));
        });
    }
}
