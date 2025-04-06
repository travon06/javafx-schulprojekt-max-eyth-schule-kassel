package models.Screens;

import java.io.ObjectInputFilter.Config;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.Texts;
import utils.config.ConfigArgument;
import utils.config.ConfigArguments;
import utils.statistics.Statistic;
import utils.statistics.Statistics;

public class StatisticsScreen {
    private Scene scene;
    private Stage stage;
    private Pane rootPane;
    private VBox statisticsVBox;
    private final int screenWidth;
    private final int screenHeight;

    public StatisticsScreen(Stage stage) {
        this.screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        this.screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        this.stage = stage;
        this.rootPane = new Pane();
        this.scene = new Scene(rootPane, screenWidth, screenHeight);
        this.statisticsVBox = new VBox(10);

        for(Statistic statistic : Statistics.STATISTICS) {
            Label statisticLabel = new Label(String.format("%s:\t%s", statistic.getArgument(), statistic.getValue()));

            statisticsVBox.getChildren().addAll(statisticLabel);
        }

        rootPane.getChildren().addAll(statisticsVBox);



        this.stage.setScene(scene);
        this.stage.setTitle(Texts.getTextByName("statisticsScreenTitle").getTextInLanguage());
        this.stage.show();
    }
}