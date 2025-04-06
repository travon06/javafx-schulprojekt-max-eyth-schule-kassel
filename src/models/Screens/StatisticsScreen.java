package models.Screens;

import graphics.Graphics;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArguments;
import utils.statistics.Statistic;
import utils.statistics.Statistics;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticsScreen {
    private Scene scene;
    private Stage stage;
    private Pane rootPane;
    private VBox statisticsVBox;
    private final int screenWidth;
    private final int screenHeight;
    private ImageView backgroundImageView;
    private Button buttonExit;

    public StatisticsScreen(Stage stage) {
        this.screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        this.screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        this.stage = stage;
        this.rootPane = new Pane();
        this.scene = new Scene(rootPane, screenWidth, screenHeight);
        this.statisticsVBox = new VBox(10);
        this.backgroundImageView = new ImageView(new Image(Graphics.getGraphicUrl("background")));
        this.backgroundImageView.setFitWidth(screenWidth);
        this.backgroundImageView.setFitHeight(screenHeight);
        this.buttonExit = new Button(Texts.getTextByName("buttonExit").getTextInLanguage());

        List<String> translatedArguments = Statistics.STATISTICS.stream()
            .filter(stat -> !stat.getArgument().equals("FIRST_TIME_IN_GAME"))
            .map(stat -> translateArgument(stat.getArgument()))
            .collect(Collectors.toList());

        int maxArgLength = translatedArguments.stream()
            .mapToInt(String::length)
            .max()
            .orElse(0);

        int totalWidth = maxArgLength + 2;

        buttonExit.setOnAction(event -> {
            new StartScreen(stage);
        });

        for (Statistic statistic : Statistics.STATISTICS) {
            String argument = statistic.getArgument();
            String value = statistic.getValue();

            if (argument.equals("FIRST_TIME_IN_GAME")) continue;

            String translated = translateArgument(argument);

            String formatted = String.format("%-" + totalWidth + "s%s", translated + ":", value);
            Label statisticLabel = new Label(formatted);
            statisticLabel.setStyle("-fx-font-family: 'monospaced';");

            statisticsVBox.getChildren().add(statisticLabel);
        }

        this.statisticsVBox.setId("statisticsVBox");

        this.rootPane.getChildren().addAll(backgroundImageView, statisticsVBox, buttonExit);
        this.rootPane.getStylesheets().add(getClass().getResource("../../style/screens.css").toExternalForm());

        this.stage.setScene(scene);
        this.stage.setTitle(Texts.getTextByName("statisticsScreenTitle").getTextInLanguage());
        this.stage.show();

        Platform.runLater(() -> {
            statisticsVBox.setLayoutX((screenWidth - statisticsVBox.getWidth()) / 2);
            statisticsVBox.setLayoutY((screenHeight - statisticsVBox.getHeight()) / 6);
            this.buttonExit.setLayoutX((screenWidth - buttonExit.getWidth()) / 2);
            this.buttonExit.setLayoutY((screenHeight - buttonExit.getHeight()) / 2);
        });

    }

    private String translateArgument(String argument) {
        return switch (argument) {
            case "LAST_LEVEL_INDEX" -> Texts.getTextByName("statisticsArgumentLastLevelIndex").getTextInLanguage();
            case "MINUTES_PLAYED" -> Texts.getTextByName("statisticsArgumentMinutesPlayed").getTextInLanguage();
            case "TIMES_CAUGHT" -> Texts.getTextByName("statisticsArgumentTimesCaught").getTextInLanguage();
            default -> argument.replace("_", " ");
        };
    }
}
