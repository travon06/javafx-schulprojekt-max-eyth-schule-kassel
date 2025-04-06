package models.Screens.MapMaker;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import language.Texts;
import models.entities.Policeman;
import utils.Waypoint;
import utils.config.ConfigArguments;
import utils.mapConfig.MapReader;

public class MapMakerMapSettings {
    private static final int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
    private static final int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    private Scene scene;
    private Pane rootPane;
    private TextField mapName;
    private ArrayList<String> mapNames;
    private ArrayList<Policeman> policemen;
    private ArrayList<Label> labels;


    public MapMakerMapSettings(Stage stage, ArrayList<Policeman> policemen) {
        this.rootPane = new Pane();
        this.scene = new Scene(rootPane, screenWidth, screenHeight);

        this.labels = new ArrayList<>();
        this.policemen = policemen;
        this.mapNames = MapReader.readMapNames(ConfigArguments.getConfigArgumentValue("MY_MAPS_PATH"));
        this.mapName = new TextField();
        this.mapName.setPromptText(Texts.getTextByName("mapName").getTextInLanguage());


        placePolicemen();

        this.rootPane.getChildren().addAll(mapName);
        stage.setScene(this.scene);
        stage.setTitle(Texts.getTextByName("mapSettings").getTextInLanguage());
        stage.show();
    }

    private void placePolicemen() {
        for(int i = 0; i < policemen.size(); i++) {
            this.rootPane.getChildren().add(policemen.get(i).getImageView());
            this.labels.add(new Label(Integer.toString(i + 1)));
            this.labels.get(i).setLayoutX(policemen.get(i).getImageView().getX());
            this.labels.get(i).setLayoutY(policemen.get(i).getImageView().getY());
            this.labels.get(i).setPrefWidth(50);
            this.labels.get(i).setPrefHeight(50);
            this.rootPane.getChildren().add(labels.get(i));
            for(Waypoint waypoint : policemen.get(i).getWaypoints()) {
                this.rootPane.getChildren().add(waypoint.getImageView());
            }
        }
    }
}
