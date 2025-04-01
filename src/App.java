import java.util.ArrayList;

import goal.Finish;
import graphics.GraphicReader;
import graphics.Graphics;
import items.EnergyDrink;
import items.Item;
import items.Key;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import language.TextReader;
import models.Gate;
import models.Screens.LevelSelection;
import models.Screens.MapMaker;
import models.Screens.StartScreen;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.BackgroundMusic;
import utils.Waypoint;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;
import utils.keyboard.KeybindingReader;
import utils.mapConfig.MapWriter;
import utils.statistics.StatisticReader;
import utils.statistics.StatisticWriter;
import utils.statistics.Statistics;
public class App extends Application {
    static {
        // load configurations
        ConfigReader.readConfig();
        KeybindingReader.readKeybindings();
        GraphicReader.readGraphics();
        TextReader.readTexts();
        StatisticReader.readStatistics();

        if(Boolean.parseBoolean(Statistics.getStatisticValue("FIRST_TIME_IN_GAME"))) {
            StatisticWriter.resetStatistics();
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        Statistics.setStatisticValue("FIRST_TIME_IN_GAME", "FALSE");

        int penis = 0;
        // 0 = Game
        // 1 = MapWriter
        // 2 = MapMaker

        if(penis == 0) {
            BackgroundMusic.getInstance().play();
            if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("DEVELOPMENT_MODE"))) {
                new LevelSelection(primaryStage);
            } else {
                new StartScreen(primaryStage);
            }

            new StartScreen(primaryStage);
        } else if (penis == 1){
            MapWriter m = new MapWriter("myFirstMap");
            int[] playerStartCoordinates = {700, 700};
            ArrayList<Tile> tiles = new ArrayList<>();
    
            tiles.add(new Tile(false, 100, 100, 50, 50, "ironRooftop"));
            tiles.add(new Tile(true, 50, 50, 50, 50, "copperRooftop"));
    
            ArrayList<Item> items = new ArrayList<>();
    
            items.add(new EnergyDrink("energyDrink", 50, 50, "energyDrink", false));
            items.add(new Key("key", 150, 150, "key", false));
    
            Finish finish = new Finish(500, 200, 50, 50, "COLLECT_ITEMS");

            int timeToSurvive = 15;

            ArrayList<Policeman> policemen = new ArrayList<>();

            Policeman p1 = new Policeman(2, 50, 60, 100, "policeman");
            Policeman p2 = new Policeman(3, 60, 70, 110, "policeman");
            
            p1.getWaypoints().add(new Waypoint(100, 50));
            p1.getWaypoints().add(new Waypoint(110, 60));
            p1.getWaypoints().add(new Waypoint(120, 70));

            p2.getWaypoints().add(new Waypoint(70,110));
            p2.getWaypoints().add(new Waypoint(70,500));
            p2.getWaypoints().add(new Waypoint(200,500));

            policemen.add(p1);
            policemen.add(p2);

            ArrayList<Item> itemsToCollect = new ArrayList<>();
            itemsToCollect.add(new EnergyDrink("energyDrink", 150, 150, "energyDrink", true));
            itemsToCollect.add(new Key("key", 1150, 1150, "key", true));

            ArrayList<Gate> gates = new ArrayList<>();

            gates.add(new Gate(50, 50, 100, 50, new ImageView(new Image(Graphics.getGraphicUrl(ConfigArguments.getConfigArgumentValue("GATE_GRAPHIC_CLOSED"))))));
            gates.add(new Gate(50, 50, 100, 50, new ImageView(new Image(Graphics.getGraphicUrl(ConfigArguments.getConfigArgumentValue("GATE_GRAPHIC_CLOSED"))))));
    
            m.createMap(playerStartCoordinates, tiles, items, policemen, gates, finish, itemsToCollect, timeToSurvive);

            // primaryStage.close();
        } else if (penis == 2) {
            new MapMaker(primaryStage);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
