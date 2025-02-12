package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.config.ConfigArguments;
import utils.keyboard.Keybindings;

public class ChangeKeyBinds {

    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button buttonWalkUp;
    private VBox vBox;
    private Button buttonTest;
    private String newButtonBind;

    public ChangeKeyBinds(Stage stage) {
        this.buttonWalkUp = new Button("W");
        this.buttonTest = new Button();
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

        this.buttonTest.setOnAction(event -> {
            System.out.println(Keybindings.getKeybindingValue("WALK_UP"));
        });


        this.vBox.getChildren().addAll(buttonWalkUp, buttonTest);
        this.rootPane.getChildren().addAll(vBox);
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
