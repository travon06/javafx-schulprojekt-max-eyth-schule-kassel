package models.Screens;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;

public class Options {
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button keyBindsButton;
    private Button buttonExit;
    private ComboBox<String> comboBox;
    private String en;
    private String de;
    private String ne;
    private VBox vBox;
    private ImageView backgroundImageView;
    private Slider slider;
    private Label labelSound;
    private GaussianBlur blur;
    
    public Options(Stage stage) {
        this.keyBindsButton = new Button(Texts.getTextByName("keyBindsButton").getTextInLanguage());
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());
        this.rootPane = new Pane();
        this.stage = stage;
        this.labelSound = new Label(ConfigArguments.getConfigArgumentValue("VOLUME"));
        this.vBox = new VBox(40);
        this.comboBox = new ComboBox<>();
        this.en = Texts.getTextByName("englisch").getTextInLanguage();
        this.de = Texts.getTextByName("deutsch").getTextInLanguage();
        this.ne = Texts.getTextByName("niederländisch").getTextInLanguage();
        this.comboBox.getItems().addAll(en, de, ne);
        this.comboBox.setValue(getLanguageForComboBox());
        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")));
        this.backgroundImageView.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")));
        this.blur = new GaussianBlur(30);
        this.backgroundImageView.setEffect(blur);
        this.slider = new Slider(0, 100, Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME")));
        this.slider.setMajorTickUnit(1);
        this.slider.setMinorTickCount(0);
        this.slider.setSnapToTicks(true);
        this.slider.setBlockIncrement(5);

        this.slider.setOnMouseReleased(event -> {
            ConfigArguments.setConfigArgumentValue("VOLUME", Double.toString(this.slider.getValue()));
            this.labelSound.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
        });
        this.slider.setOnKeyReleased(event -> {
            ConfigArguments.setConfigArgumentValue("VOLUME", Double.toString(this.slider.getValue()));
            this.labelSound.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
        });



        this.scene = new Scene(
            this.rootPane,
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );
        this.keyBindsButton.setOnAction(event -> {
            ChangeKeyBinds changeKeyBinds = new ChangeKeyBinds(stage);
        });
        this.buttonExit.setOnAction(event -> {
            StartScreen startScreen = new StartScreen(stage);
        });
        this.comboBox.setOnAction(event -> {
            getLanguage(this.comboBox.getValue());
            Options options = new Options(stage);
        });
        Platform.runLater(() -> {
            double width = vBox.getWidth();
            double height = vBox.getHeight();
            this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - width) / 2);
            this.vBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - height) / 2) - 100);
        
            // Berechne die mittige Y-Position des Sliders
            double sliderMidY = slider.localToScene(0, 0).getY() + slider.getHeight() / 2;
            
            // Setze die Y-Position des labelSound, sodass es mittig über dem Slider ist
            labelSound.setLayoutY(sliderMidY - labelSound.getHeight() / 2);
            labelSound.setLayoutX(slider.localToScene(0, 0).getX() - 60);  // Horizontal links vom Slider
        
            // Position des labelSound zuweisen
            this.labelSound.setText(Integer.toString((int) Double.parseDouble(ConfigArguments.getConfigArgumentValue("VOLUME"))));
        });
        
        this.vBox.getChildren().addAll(keyBindsButton, comboBox, slider, buttonExit);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());
        this.vBox.setAlignment(Pos.CENTER);
        this.rootPane.getChildren().addAll(backgroundImageView, vBox, labelSound);
        this.vBox.setLayoutX((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")) - vBox.getWidth()) / 2);
        this.vBox.setLayoutY(((Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT")) - vBox.getHeight()) / 2) - 100);
        this.stage.setScene(this.scene);
        this.stage.setTitle(Texts.getTextByName("OptionsScreen").getTextInLanguage());
        this.stage.show();
    }

    public void getLanguage(String comboBoxWert) {
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

    //#region getter & setter
    
    public ImageView getBackgroundImageView() {
        return backgroundImageView;
    }

    public Button getButtonExit() {
        return buttonExit;
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public String getDe() {
        return de;
    }

    public String getEn() {
        return en;
    }

    public Button getKeyBindsButton() {
        return keyBindsButton;
    }

    public String getNe() {
        return ne;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public Scene getScene() {
        return scene;
    }

    public Slider getSlider() {
        return slider;
    }

    public Stage getStage() {
        return stage;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setBackgroundImageView(ImageView backgroundImageView) {
        this.backgroundImageView = backgroundImageView;
    }

    public void setButtonExit(Button buttonExit) {
        this.buttonExit = buttonExit;
    }
    
    public void setComboBox(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public void setEn(String en) {
        this.en = en;
    }
    
    public void setKeyBindsButton(Button keyBindsButton) {
        this.keyBindsButton = keyBindsButton;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    //#endregion
}