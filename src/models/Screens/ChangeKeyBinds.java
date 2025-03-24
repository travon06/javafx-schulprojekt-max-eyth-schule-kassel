package models.Screens;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;
import utils.keyboard.Keybinding;
import utils.keyboard.Keybindings;

public class ChangeKeyBinds {

    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button buttonExit;
    private VBox vBox;
    private VBox vBoxText;
    private String newButtonBind;
    private ImageView backgroundImageView;
    private GaussianBlur blur;

    public ChangeKeyBinds(Stage stage) {
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.rootPane = new Pane();
        this.stage = stage;
        this.vBox = new VBox(20);
        this.vBoxText = new VBox(20);
        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.blur = new GaussianBlur(30);
        this.backgroundImageView.setEffect(blur);
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        for(Keybinding keybinding : Keybindings.getKeybindings()) {
            Button button = new Button(keybinding.getValue());
            button.setOnAction(event -> {
                button.setText("");
                changeKeyBind(keybinding.getArgument(), button);
            });
            this.vBox.getChildren().add(button);
            Label label = new Label(Texts.getTextByName(keybinding.getArgument()).getTextInLanguage());
            this.vBoxText.getChildren().add(label);
        }

        this.buttonExit.setOnAction(event -> {
            new Options(stage);
        });

        Platform.runLater(() -> {
            this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - vBox.getWidth()) * 6 / 9);
            this.vBox.setLayoutY((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - vBox.getHeight()) / 4);
            this.vBoxText.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - vBox.getWidth()) * 2 / 9);
            this.vBoxText.setLayoutY((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - vBox.getHeight()) / 4);
        });

        Platform.runLater(() -> {
            double width = this.buttonExit.getWidth();
            double height = this.vBox.getLayoutY() + this.vBox.getHeight();
            this.buttonExit.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
            this.buttonExit.setLayoutY(height + (Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height - this.buttonExit.getHeight()) / 2 );
        });


        this.scene.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().addAll(backgroundImageView, vBoxText, vBox, buttonExit);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("ChangeKeyBindsScreen").getTextInLanguage());
        this.stage.show();
    }

    public void changeKeyBind(String keyToChange, Button button) {
        this.stage.getScene().setOnKeyPressed(keyEvent -> {
            this.newButtonBind = keyEvent.getCode().toString();
            Keybindings.setKeybindingValue(keyToChange, newButtonBind);
            this.stage.getScene().setOnKeyPressed(null);
            button.setText(Keybindings.getKeybindingValue(keyToChange));
        });
    }
}