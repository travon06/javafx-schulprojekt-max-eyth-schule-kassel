package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
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
    
    public Options(Stage stage) {
        this.keyBindsButton = new Button(Texts.getTextByName("keyBindsButton").getTextInLanguage());
        this.rootPane = new Pane();
        this.stage = stage;
        this.comboBox = new ComboBox<>();
        this.en = Texts.getTextByName("englisch").getTextInLanguage();
        this.de = Texts.getTextByName("deutsch").getTextInLanguage();
        this.ne = Texts.getTextByName("niederländisch").getTextInLanguage();
        
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.keyBindsButton.setOnAction(event -> {
            ChangeKeyBinds changeKeyBinds = new ChangeKeyBinds(this.stage);
        });

        this.comboBox.getItems().addAll(en, de, ne);
        this.comboBox.setValue(en); //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        comboBox.setOnAction(event -> {
            getLanguage(this.comboBox.getValue());
        });

        this.rootPane.getChildren().addAll(/*keyBindsButton,*/ comboBox);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§");
        this.stage.show();
    }

    public void toggleLanguage() {

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
        System.out.println("Sprache geändert: " + neueSprache);
        return neueSprache;
    }
    
}
