package models.Screens;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class Options {
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button keyBindsButton;
    private ComboBox<String> comboBox;
    private String en;
    private String de;
    private String ne;
    private VBox vBox;
    
    public Options(Stage stage) {
        this.keyBindsButton = new Button(Texts.getTextByName("keyBindsButton").getTextInLanguage());
        this.rootPane = new Pane();
        this.stage = stage;
        this.comboBox = new ComboBox<>();
        this.vBox = new VBox(60);
        this.en = Texts.getTextByName("englisch").getTextInLanguage();
        this.de = Texts.getTextByName("deutsch").getTextInLanguage();
        this.ne = Texts.getTextByName("niederländisch").getTextInLanguage();
        
        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.keyBindsButton.setOnAction(event -> {
            ChangeKeyBinds changeKeyBinds = new ChangeKeyBinds(stage);
        });

        this.comboBox.getItems().addAll(en, de, ne);
        this.comboBox.setValue(getLanguageForComboBox());
        comboBox.setOnAction(event -> {
            getLanguage(this.comboBox.getValue());
        });

        Platform.runLater(() -> {
            double width = vBox.getWidth();
            this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
            double height = vBox.getHeight();
            this.vBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height) / 2) - 100);
        });

        this.vBox.getChildren().addAll(keyBindsButton, comboBox);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().addAll(vBox);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§");
        this.stage.show();
    }

    public String getLanguage(String comboBoxWert) {
        String neueSprache;
        switch(comboBoxWert) {
            case "English":
            case "Englisch":
            case "Engles": neueSprache = "EN"; break;

            case "German":
            case "Deutsch":
            case "Duits": neueSprache = "DE"; break;

            case "Dutch":
            case "Niederländisch":
            case "Nederlands": neueSprache = "NE"; break;
            
            default: neueSprache = "EN"; break;
        }
        for (int i = 0; i < ConfigArguments.getConfigArguments().size(); i++) {
            if(ConfigArguments.getConfigArguments().get(i).getArgument().equals("LANGUAGE")) {
                ConfigArguments.getConfigArguments().get(i).setValue(neueSprache);
            }
        }
        return neueSprache;
    }

    public String getLanguageForComboBox() {
        String language = (ConfigArguments.getConfigArgumentValue("LANGUAGE"));
        switch(language) {
            case "EN": return this.en;
            case "DE": return this.de;
            case "NE": return this.ne;
        }
        return "English";
    }
    
}
