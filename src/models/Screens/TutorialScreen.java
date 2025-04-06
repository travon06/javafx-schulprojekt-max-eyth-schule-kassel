package models.Screens;

import graphics.Graphic;
import graphics.Graphics;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class TutorialScreen {
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private final int screenWidth;
    private final int screenHeight;
    private VBox tutorialVBox;
    private ImageView backgroundImageView;
    private Button buttonExit;

    public TutorialScreen(Stage stage) {
        screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.stage = stage;
        this.rootPane = new Pane();
        this.scene = new Scene(rootPane, screenWidth, screenHeight);
        this.tutorialVBox = new VBox(20); // 20px spacing between the elements

        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(screenWidth);
        this.backgroundImageView.setFitHeight(screenHeight);

        this.buttonExit.setOnAction(event -> {
            new StartScreen(stage);
        });

        ImageView policemanImageView = new ImageView(new Image(Graphics.getGraphicUrl("policeman")));
        policemanImageView.setFitWidth(50);
        policemanImageView.setFitHeight(50);
        Label policemanLabel = new Label(Texts.getTextByName("tutorialPoliceman").getTextInLanguage());
        HBox policemanHBox = new HBox();
        policemanHBox.getChildren().addAll(policemanImageView, policemanLabel);
        policemanHBox.setId("tutorialHBox");

        ImageView energyDrinkImageView = new ImageView(new Image(Graphics.getGraphicUrl("energyDrink")));
        energyDrinkImageView.setFitWidth(50);
        energyDrinkImageView.setFitHeight(50);
        Label energyDrinkLabel = new Label(Texts.getTextByName("tutorialEnergyDrink").getTextInLanguage());
        HBox energyDrinkHBox = new HBox();
        energyDrinkHBox.getChildren().addAll(energyDrinkImageView, energyDrinkLabel);
        energyDrinkHBox.setId("tutorialHBox");

        ImageView potionImageView = new ImageView(new Image(Graphics.getGraphicUrl("energyDrink")));
        potionImageView.setFitWidth(50);
        potionImageView.setFitHeight(50);
        Label potionLabel = new Label(Texts.getTextByName("tutorialPotion").getTextInLanguage());
        HBox potionHBox = new HBox();
        potionHBox.getChildren().addAll(potionImageView, potionLabel);
        potionHBox.setId("tutorialHBox");

        ImageView gateImageView = new ImageView(new Image(Graphics.getGraphicUrl("gateClosed")));
        gateImageView.setFitWidth(100);
        gateImageView.setFitHeight(50);
        Label gateLabel = new Label(Texts.getTextByName("tutorialGate").getTextInLanguage());
        HBox gateHBox = new HBox();
        gateHBox.getChildren().addAll(gateImageView, gateLabel);
        gateHBox.setId("tutorialHBox");

        ImageView goalImageView = new ImageView(new Image(Graphics.getGraphicUrl("finishGold")));
        goalImageView.setFitWidth(50);
        goalImageView.setFitHeight(50);
        Label goalLabel = new Label(Texts.getTextByName("tutorialGoal").getTextInLanguage());
        HBox goalHBox = new HBox();
        goalHBox.getChildren().addAll(goalImageView, goalLabel);
        goalHBox.setId("tutorialHBox");

        // Add the HBox elements to the VBox
        
        rootPane.getChildren().addAll(backgroundImageView, tutorialVBox, buttonExit);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        
        this.stage.setScene(scene);
        this.stage.setTitle(Texts.getTextByName("tutorial").getTextInLanguage());
        this.stage.show();

        this.tutorialVBox.getChildren().addAll(policemanHBox, energyDrinkHBox, potionHBox, gateHBox, goalHBox);
        this.tutorialVBox.setAlignment(Pos.CENTER); // Center the VBox content
        this.tutorialVBox.setPrefWidth(screenWidth);
        this.tutorialVBox.setPrefHeight(screenHeight - 200);
    
        // Place the exit button below the VBox
        this.buttonExit.setLayoutX((screenWidth - buttonExit.getWidth()) / 2); // Center the button horizontally
        this.buttonExit.setLayoutY(screenHeight / 2 + tutorialVBox.getHeight() / 2 + 200); // Place the button below the VBox
    }
}
