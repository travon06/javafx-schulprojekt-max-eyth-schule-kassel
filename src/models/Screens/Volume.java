package models.Screens;

import graphics.Graphics;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class Volume {
    private Slider sliderMasterVolume;
    private Label labelSliderMasterVolume;
    private Label labelMasterVolume;
    private Pane rootPane;
    private Stage stage;
    private ImageView backgroundImageView;
    private GaussianBlur blur;
    private Scene scene;
    private Button buttonExit;
    private VBox vBox;
    private AnimationTimer timer;

    public Volume(Stage stage) {
        this.rootPane = new Pane();
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.sliderMasterVolume = new Slider();
        this.labelSliderMasterVolume = new Label(ConfigArguments.getConfigArgumentValue("VOLUME"));
        this.labelMasterVolume = new Label(Texts.getTextByName("buttonVolume").getTextInLanguage());
        this.stage = stage;
        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.blur = new GaussianBlur(30);
        this.backgroundImageView.setEffect(blur);
        this.sliderMasterVolume = new Slider(0, 100, Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME")));
        this.sliderMasterVolume.setMajorTickUnit(1);
        this.sliderMasterVolume.setMinorTickCount(0);
        this.sliderMasterVolume.setSnapToTicks(true);
        this.sliderMasterVolume.setBlockIncrement(5);
        this.vBox = new VBox(40);

        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ConfigArguments.setConfigArgumentValue("VOLUME", Double.toString(sliderMasterVolume.getValue()));
                labelSliderMasterVolume.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
            }
        };
        this.sliderMasterVolume.setOnMousePressed(event -> {
            this.timer.start();
        });
        this.sliderMasterVolume.setOnMouseReleased(event -> {
            this.timer.stop();
            ConfigArguments.setConfigArgumentValue("VOLUME", Double.toString(this.sliderMasterVolume.getValue()));
            this.labelSliderMasterVolume.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
        });
        this.sliderMasterVolume.setOnKeyReleased(event -> {
            ConfigArguments.setConfigArgumentValue("VOLUME", Double.toString(this.sliderMasterVolume.getValue()));
            this.labelSliderMasterVolume.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
        });
        this.buttonExit.setOnAction(event -> {
            new Options(stage);
        });
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        Platform.runLater(() -> {
            this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - vBox.getWidth()) * 11 / 18);
            this.vBox.setLayoutY((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - vBox.getHeight()) / 4);
            double sliderMasterVolumeMidY = sliderMasterVolume.localToScene(0, 0).getY() + sliderMasterVolume.getHeight() / 2;
            this.labelSliderMasterVolume.setLayoutY(sliderMasterVolumeMidY - labelSliderMasterVolume.getHeight() / 2);
            this.labelSliderMasterVolume.setLayoutX(sliderMasterVolume.localToScene(0, 0).getX() - 60);
            this.labelSliderMasterVolume.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
            this.labelMasterVolume.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - labelMasterVolume.getWidth()) * 7 / 18);
            this.labelMasterVolume.setLayoutY(labelSliderMasterVolume.getLayoutY());
            this.buttonExit.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - this.buttonExit.getWidth()) / 2);
            this.buttonExit.setLayoutY(this.vBox.getLayoutY() + this.vBox.getHeight() + (Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - this.vBox.getLayoutY() + this.vBox.getHeight() - this.buttonExit.getHeight()) / 2 );
        });


        this.vBox.getChildren().addAll(sliderMasterVolume);
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.rootPane.getChildren().addAll(backgroundImageView, vBox, labelMasterVolume, labelSliderMasterVolume, buttonExit);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("buttonVolume").getTextInLanguage());
        this.stage.show();
    }

}
