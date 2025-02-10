package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.config.ConfigArguments;

public class ChangeKeyBinds {

    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button buttonTest;

    public ChangeKeyBinds(Stage stage) {
        this.buttonTest = new Button("Test");
        this.rootPane = new Pane();
        this.stage = stage;
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        
        this.buttonTest.setOnAction(event -> {
            this.buttonTest.setText("");

        });

        this.rootPane.getChildren().addAll(buttonTest);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§§§§§§§§");
        this.stage.show();
    }
}
