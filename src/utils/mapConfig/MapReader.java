package utils.mapConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.shape.Rectangle;
import models.Item;

public class MapReader {
    public static ArrayList<Rectangle> readObstacles(String mapName) {
        ArrayList<Rectangle> obstacleRectangles = new ArrayList<>();  
        ArrayList<String> maps = MapReader.getMaps();

        for(String line : maps.get(1).split("&")) {
            System.out.println(line);
        }
    
        for (String map : maps) {
            if (map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");
    
                for (String argument : arguments) {
                    if (argument.startsWith("!obstacles")) {
                        String[] obstacles = argument.split(":")[1].split(";");
    
                        for (String obstacle : obstacles) {
                            obstacle = obstacle.replace("(", "").replace(")", "");
                            String[] obstacleArguments = obstacle.split(",");
    
                            if (obstacleArguments.length == 4) { // Ensure 4 arguments
                                obstacleRectangles.add(new Rectangle(
                                    Integer.parseInt(obstacleArguments[0]), // x
                                    Integer.parseInt(obstacleArguments[1]), // y
                                    Integer.parseInt(obstacleArguments[2]), // width
                                    Integer.parseInt(obstacleArguments[3])  // height
                                ));
                            } else {
                                System.err.println("Invalid obstacle format: " + obstacle);           
                            }
                        }
                    }
                }
            }
        }
        return obstacleRectangles;
    }

    public static ArrayList<Item> readItems(String mapName) {
        ArrayList<Item> items = new ArrayList<>();

        ArrayList<String> maps = MapReader.getMaps();

        for(String map : maps) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!items:")) {
                        String[] itemStrings = argument.split(":")[1].split(";");

                        for(String item : itemStrings) {
                            item = item.replace("(", "");
                            item = item.replace(")", "");

                            String[] itemArguments = item.split(",");

                            items.add(new Item(
                                itemArguments[0], 
                                Integer.parseInt(itemArguments[1]), 
                                Integer.parseInt(itemArguments[2]), 
                                Integer.parseInt(itemArguments[3])
                            ));

                        }
                    }
                }   
            }
        }
        return items;
    }

    private static ArrayList<String> getMaps() {
        ArrayList<String> maps = new ArrayList<>();
        Path mapsPath = Paths.get("src/utils/mapConfig/maps.txt");
        Path absoluteMapsPath = mapsPath.toAbsolutePath();
    
        if (Files.exists(absoluteMapsPath)) {
            try {
                String data = Files.readString(absoluteMapsPath);
                data = data.replaceAll("\\s", ""); // Remove all whitespace
                for(String map : data.split("!mapEnd")) {
                    maps.add(map);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in obstacles data!");
            }
        } else {
            System.err.println(String.format("File: '%s' does not exist!", absoluteMapsPath.toString()));
        }
        return maps;
    }
    

    // public static void main(String[] args) {
    //     MapReader.readObstacles("level1");
    //     for(Item rec: MapReader.readItems("level2")) {
    //         System.out.println(rec);
    //     } 
    // }
}
