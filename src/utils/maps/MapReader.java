package utils.maps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.entities.Player;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;

public class MapReader {
    public static ArrayList<Rectangle> readObstacles(String mapName) {
        Path mapsPath = Paths.get("src/utils/maps/maps.txt");
        Path absoluteMapsPath = mapsPath.toAbsolutePath();

        if (Files.exists(absoluteMapsPath)) {

            try (BufferedReader reader = new BufferedReader(new FileReader(absoluteMapsPath.toString()))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank() && line.equals(String.format("!map:%s", mapName))) {
                        while(!((line = reader.readLine()).equals("!mapEnd"))) {
                            if(line.startsWith("!obstacles")) {
                                String obstacles[] = line.split(":")[1].split(";");
                                ArrayList<Rectangle> obstacleRectangles = new ArrayList<>();

                                for(String obstacle : obstacles) {
                                    obstacle = obstacle.replace("(", "");
                                    obstacle = obstacle.replace(")", "");

                                    obstacleRectangles.add(new Rectangle(
                                    Integer.parseInt(obstacle.split(",")[0]),
                                    Integer.parseInt(obstacle.split(",")[1]),
                                    Integer.parseInt(obstacle.split(",")[2]),
                                    Integer.parseInt(obstacle.split(",")[3])
                                    ));
                                }

                                return obstacleRectangles;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading map file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Map file does not exist: " + absoluteMapsPath);
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        ConfigReader.readConfig();
        for(Rectangle rec: MapReader.readObstacles("level1")) {
            System.out.println(rec);
        } 
    }
}
