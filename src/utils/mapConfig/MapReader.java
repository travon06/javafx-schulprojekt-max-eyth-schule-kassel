package utils.mapConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Item;
import models.entities.Policeman;
import utils.Waypoint;
import utils.config.ConfigArguments;

public class MapReader {

    private final static ArrayList<String> MAPS = MapReader.getMaps();

    public static ArrayList<Rectangle> readObstacles(String mapName) {
        ArrayList<Rectangle> obstacleRectangles = new ArrayList<>(); 
    
        for (String map : MapReader.MAPS) {
            if (map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");
    
                for (String argument : arguments) {
                    if (argument.startsWith("!obstacles")) {
                        String[] obstacles = argument.split(":")[1].split(";");

                        

                        for (int i = 0; i < obstacles.length; i++) {
                            if(obstacles[i].equalsIgnoreCase("border")) {
                                int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
                                int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
                                int borderBounds = 10;

                                // top border
                                obstacleRectangles.add(new Rectangle(
                                    0,
                                    0 - borderBounds,
                                    screenWidth + borderBounds,
                                    borderBounds
                                ));
                                
                                // right border
                                obstacleRectangles.add(new Rectangle(
                                    screenWidth,
                                    0 - borderBounds,
                                    borderBounds,
                                    screenHeight + borderBounds
                                ));

                                // bottom border
                                obstacleRectangles.add(new Rectangle(
                                    0,
                                    screenHeight,
                                    screenWidth + borderBounds,
                                    borderBounds
                                ));

                                // left border
                                obstacleRectangles.add(new Rectangle(
                                    0 - borderBounds,
                                    0 - borderBounds,
                                    borderBounds,
                                    screenHeight + borderBounds
                                ));
                                continue;
                            }

                            obstacles[i] = obstacles[i].replace("(", "").replace(")", "");
                            String[] obstacleArguments = obstacles[i].split(",");
    
                            if (obstacleArguments.length == 4) { // Ensure 4 arguments
                                obstacleRectangles.add(new Rectangle(
                                    Integer.parseInt(obstacleArguments[0]), // x
                                    Integer.parseInt(obstacleArguments[1]), // y
                                    Integer.parseInt(obstacleArguments[2]), // width
                                    Integer.parseInt(obstacleArguments[3])  // height
                                ));
                            } else {
                                System.err.println("Invalid obstacle format: " + obstacles[i]);           
                            }
                        }
                    }
                }
            }
        }
        return obstacleRectangles;
    }

    public static ArrayList<Policeman> readpolicemen(String mapName) {
        ArrayList<Policeman> policemen = new ArrayList<>();

        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!policemen:")) {
                        String[] policementrings = argument.split(":")[1].split(";");

                        for(String policeman : policementrings) {
                            policeman = policeman.replace("(", "");
                            policeman = policeman.replace(")", "");

                            String[] policemanArguments = policeman.split(",");

                            int policemanSpeed = Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_STANDART_SPEED"));

                            // if speed for policeman is given set this as the speed else set speed to POLICEMAN_STANDART_SPEED from config.txt
                            if(policemanArguments.length >= 3) {
                                policemanSpeed = Integer.parseInt(policemanArguments[2]);
                            }

                            policemen.add(new Policeman(
                                policemanSpeed, 
                                Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HEALTH")),

                                new Rectangle(
                                    Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")), 
                                    Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")),
                                    Color.DARKBLUE
                                ),

                                Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")),
                                Integer.parseInt(policemanArguments[0]),
                                Integer.parseInt(policemanArguments[1])
                            ));

                        }
                    }
                }   
            }
        }
        return policemen;
    }

    public static ArrayList<Item> readItems(String mapName) {
        ArrayList<Item> items = new ArrayList<>();


        for(String map : MapReader.MAPS) {
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

    public static ArrayList<Waypoint> readWaypoints(String mapName, int policemanIndex) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();


        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");


                for(String argument : arguments) { 
                    if(argument.startsWith("!policemanWaypoints:")) {
                        String waypointData = argument.split(":")[1];
                        waypointData = waypointData.replace("{", "").replace("}", "");

                        String[] waypointArray = waypointData.split("]");


                        waypointArray[policemanIndex] = waypointArray[policemanIndex].replace("[", "");

                        String[] waypointCoordinates = waypointArray[policemanIndex].split(";");

                        for(String waypointCoordiante : waypointCoordinates) {
                            waypointCoordiante = waypointCoordiante.replace("(", "").replace(")", "");

                            int x = Integer.parseInt(waypointCoordiante.split(",")[0]);
                            int y = Integer.parseInt(waypointCoordiante.split(",")[1]);
                            
                            boolean allowVerticalMovement = false;

                            // if allowVertical was given in maps.txt
                            if(waypointCoordiante.split(",").length == 3) {
                                System.out.println("####aagsiidfbsijbi");
                                allowVerticalMovement = Boolean.parseBoolean(waypointCoordiante.split(",")[2]);
                            }

                            waypoints.add(new Waypoint(x, y, allowVerticalMovement));
                        }     
                    }
                }   
            }
        }
        return waypoints;
    }

    public static int[] readPlayerStartCoordinates(String mapName) {
        int[] playerStartCoordinates = new int[2];


        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");
    
    
                for(String argument : arguments) { 
                    if(argument.startsWith("!playerStartCoordinates")) {
                        argument = argument.split(":")[1];

                        playerStartCoordinates[0] = Integer.parseInt(argument.split(",")[0]);
                        playerStartCoordinates[1] = Integer.parseInt(argument.split(",")[1]);
                    }
                }   
            }
        }
        return playerStartCoordinates;
    }

    private static ArrayList<String> getMaps() {
        ArrayList<String> maps = new ArrayList<>();
        Path mapsPath = Paths.get("src/utils/mapConfig/maps.txt");
        Path absoluteMapsPath = mapsPath.toAbsolutePath();
    
        if (Files.exists(absoluteMapsPath)) {
            try {
                String data = Files.readString(absoluteMapsPath);
                data = data.replaceAll("\\s", ""); // Remove all whitespaces

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
    //     MapReader.getMaps();
    // }
}
