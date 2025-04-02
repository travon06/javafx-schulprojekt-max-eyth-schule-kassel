package utils.mapConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import items.Key;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import goal.Finish;
import graphics.Graphics;
import language.Texts;
import items.Coat;
import items.EnergyDrink;
import items.Item;
import models.Gate;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.Waypoint;
import utils.config.ConfigArguments;

public class MapReader {

    public static ArrayList<Tile> readTiles(String mapName, String mapsPath) {
        ArrayList<Tile> tiles = new ArrayList<>();

        initializeBackground(tiles);
        
        for (String map : MapReader.getMaps(mapsPath)) {
            if (map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");
    
                for (String argument : arguments) {
                    if (argument.startsWith("!tiles")) {
                        String[] obstacles = argument.split(":")[1].split(";");

                        
                        boolean borderIsSet = false;
                        for (int i = 0; i < obstacles.length; i++) {
                            if(obstacles[i].equalsIgnoreCase("border") && !borderIsSet) {
                                initializeBorder(tiles);
                                borderIsSet = true;
                                continue;
                            }

                            obstacles[i] = obstacles[i].replace("(", "").replace(")", "");
                            String[] obstacleArguments = obstacles[i].split(",");

                            Boolean isBool = obstacleArguments[0].equalsIgnoreCase("true") || obstacleArguments[0].equalsIgnoreCase("false");
                            int tileBounds = Integer.parseInt(ConfigArguments.getConfigArgumentValue("TILE_BOUNDS"));

                            if (isBool && obstacleArguments.length == 6) {
                                if (Integer.parseInt(obstacleArguments[3]) % tileBounds != 0 || Integer.parseInt(obstacleArguments[4]) % tileBounds != 0) {
                                    throw new Error("idk");
                                } else {
                                    int startX = Integer.parseInt(obstacleArguments[1]);
                                    int startY = Integer.parseInt(obstacleArguments[2]) + tileBounds;
                                    int x = startX;
                                    int y = startY;
                            
                                    for (int j = 0; j < Integer.parseInt(obstacleArguments[3]) / tileBounds; j++) {
                                        for (int k = 0; k < Integer.parseInt(obstacleArguments[4]) / tileBounds; k++) {
                                            tiles.add(new Tile(
                                                Boolean.parseBoolean(obstacleArguments[0]),
                                                x,
                                                y,
                                                tileBounds,
                                                tileBounds,
                                                obstacleArguments[5]
                                            ));
                                            y += tileBounds;
                                        }
                                        x += tileBounds;
                                        y = startY;
                                    }
                                }
                            } else if (!isBool && obstacleArguments.length == 5) {
                                if (Integer.parseInt(obstacleArguments[2]) % tileBounds != 0 || Integer.parseInt(obstacleArguments[3]) % tileBounds != 0) {
                                    throw new Error("idk");
                                } else {
                                    int startX = Integer.parseInt(obstacleArguments[0]);
                                    int startY = Integer.parseInt(obstacleArguments[1]) + tileBounds;
                                    int x = startX;
                                    int y = startY;
                            
                                    for (int j = 0; j < Integer.parseInt(obstacleArguments[2]) / tileBounds; j++) {
                                        for (int k = 0; k < Integer.parseInt(obstacleArguments[3]) / tileBounds; k++) {
                                            tiles.add(new Tile(
                                                true,
                                                x,
                                                y,
                                                tileBounds,
                                                tileBounds,
                                                obstacleArguments[4]
                                            ));
                                            y += tileBounds;
                                        }
                                        x += tileBounds;
                                        y = startY;
                                    }
                                }
                            } else if (isBool && obstacleArguments.length == 5) {
                                if (Integer.parseInt(obstacleArguments[2]) % tileBounds != 0 || Integer.parseInt(obstacleArguments[3]) % tileBounds != 0) {
                                    throw new Error("idk");
                                } else {
                                    int startX = Integer.parseInt(obstacleArguments[1]);
                                    int startY = Integer.parseInt(obstacleArguments[2]) + tileBounds;
                                    int x = startX;
                                    int y = startY;
                            
                                    for (int j = 0; j < Integer.parseInt(obstacleArguments[3]) / tileBounds; j++) {
                                        for (int k = 0; k < Integer.parseInt(obstacleArguments[4]) / tileBounds; k++) {
                                            tiles.add(new Tile(
                                                Boolean.parseBoolean(obstacleArguments[0]),
                                                x,
                                                y,
                                                tileBounds,
                                                tileBounds
                                            ));
                                            y += tileBounds;
                                        }
                                        x += tileBounds;
                                        y = startY;
                                    }
                                }
                            } else if (!isBool && obstacleArguments.length == 4) {
                                if (Integer.parseInt(obstacleArguments[2]) % tileBounds != 0 || Integer.parseInt(obstacleArguments[3]) % tileBounds != 0) {
                                    throw new Error("idk");
                                } else {
                                    int startX = Integer.parseInt(obstacleArguments[0]);
                                    int startY = Integer.parseInt(obstacleArguments[1]) + tileBounds;
                                    int x = startX;
                                    int y = startY;
                            
                                    for (int j = 0; j < Integer.parseInt(obstacleArguments[2]) / tileBounds; j++) {
                                        for (int k = 0; k < Integer.parseInt(obstacleArguments[3]) / tileBounds; k++) {
                                            tiles.add(new Tile(
                                                true,
                                                x,
                                                y,
                                                tileBounds,
                                                tileBounds
                                            ));
                                            y += tileBounds;
                                        }
                                        x += tileBounds;
                                        y = startY;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return tiles;
    }

    private static void initializeBackground(ArrayList<Tile> tiles) {
        int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        int tileBounds = 50;

        for(int i = 0; i < screenWidth; i += tileBounds) {
            for(int j = 0; j < screenHeight; j += tileBounds) {
                tiles.add(new Tile(false, i, j, tileBounds, tileBounds, "gras"));
            }
        }
    }

    private static void initializeBorder(ArrayList<Tile> tiles) {
        int screenWidth = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        int screenHeight = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
        int borderBounds = 10;

        // top border
        tiles.add(new Tile(
            true,
            0,
            0 - borderBounds,
            screenWidth + borderBounds,
            borderBounds
        ));
        
        // right border
        tiles.add(new Tile(
            true,
            screenWidth,
            0 - borderBounds,
            borderBounds,
            screenHeight + borderBounds
        ));

        // bottom border
        tiles.add(new Tile(
            true,
            0,
            screenHeight,
            screenWidth + borderBounds,
            borderBounds
        ));

        // left border
        tiles.add(new Tile(
            true,
            0 - borderBounds,
            0 - borderBounds,
            borderBounds,
            screenHeight + borderBounds
        ));
    }

    public static ArrayList<Policeman> readPolicemen(String mapName, String mapsPath) {
        ArrayList<Policeman> policemen = new ArrayList<>();

        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!policemen:")) {
                        String[] policemenStrings = argument.split(":")[1].split(";");

                        for(String policeman : policemenStrings) {
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
                                Integer.parseInt(ConfigArguments.getConfigArgumentValue("POLICEMAN_HITBOX_BOUNDS")),
                                Integer.parseInt(policemanArguments[0]),
                                Integer.parseInt(policemanArguments[1]),
                                ConfigArguments.getConfigArgumentValue("POLICEMEN_GRAPHIC_NAME")
                            ));
                        }
                    }
                }   
            }
        }
        return policemen;
    }

    public static ArrayList<Item> readItems(String mapName, String mapsPath) {
        ArrayList<Item> items = new ArrayList<>();


        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!items:")) {
                        String[] itemStrings = argument.split(":")[1].split(";");

                        for(String item : itemStrings) {
                            item = item.replace("(", "");
                            item = item.replace(")", "");

                            String[] itemArguments = item.split(",");


                            if(itemArguments[0].equals("key")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        false
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        false
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("energyDrink")) {
                                if(itemArguments.length == 3) {
                                    items.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        false
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        itemArguments[3],
                                        false
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("coat")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        false
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        false
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("lock")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        false
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        false
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } 

                        }
                    }
                }   
            }
        }
        return items;
    }

    public static ArrayList<Waypoint> readWaypoints(String mapName, String mapsPath, int policemanIndex) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) { 
                    if(argument.startsWith("!policemenWaypoints:")) {
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

    public static int[] readPlayerStartCoordinates(String mapName, String mapsPath) {
        int[] playerStartCoordinates = new int[2];


        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
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

    public static ArrayList<String> getMaps(String mapsPathString) {
        ArrayList<String> maps = new ArrayList<>();
        Path mapsPath = Paths.get(mapsPathString);
        Path absoluteMapsPath = mapsPath.toAbsolutePath();
    
        if (Files.exists(absoluteMapsPath)) {
            try {
                String data = Files.readString(absoluteMapsPath);
                data = data.replaceAll("\\s", ""); // Remove all whitespaces

                for(String map : data.split("!mapEnd&")) {
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

    public static int readTimeToSurvive(String mapName, String mapsPath) {
        int timeToSurvive = 0;
        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");
    
    
                for(String argument : arguments) { 
                    if(argument.startsWith("!timeToSurvive:")) {
                        timeToSurvive = Integer.parseInt(argument.split(":")[1]);
                    }
                }   
            }
        }
        return timeToSurvive;
    }

    public static Finish readFinish(String mapName, String mapsPath) {
        Finish finish = null;

        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) { 
                    if(argument.startsWith("!finish")) {
                        argument = argument.split(":")[1];
                        argument = argument.replace("(", "").replace(")", "");
                        String[] finishArguments = argument.split(",");

                        finish = new Finish(
                            Integer.parseInt(finishArguments[0]), // x
                            Integer.parseInt(finishArguments[1]), // y
                            Integer.parseInt(ConfigArguments.getConfigArgumentValue("GOAL_BOUNDS")), // width
                            Integer.parseInt(ConfigArguments.getConfigArgumentValue("GOAL_BOUNDS")), // height
                            finishArguments[2] // goal
                        );
                    }
                }   
            }
        }
        return finish;
    }

    public static ArrayList<Gate> readGates(String mapName, String mapsPath) {
        ArrayList<Gate> gates = new ArrayList<>();

        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");
    
    
                for(String argument : arguments) { 
                    if(argument.startsWith("!gates")) {
                        argument = argument.split(":")[1];

                        String[] gateStrings = argument.split(";");

                        for(String gateString : gateStrings) {
                            String[] gateArguments = gateString.split(",");

                            gates.add(new Gate(
                                Integer.parseInt(gateArguments[0]),
                                Integer.parseInt(gateArguments[1]),
                                Integer.parseInt(ConfigArguments.getConfigArgumentValue("GATE_WIDTH")), 
                                Integer.parseInt(ConfigArguments.getConfigArgumentValue("GATE_HEIGHT")),
                                new ImageView(new Image(Graphics.getGraphicUrl(ConfigArguments.getConfigArgumentValue("GATE_GRAPHIC_CLOSED"))))
                            ));
                        }
                    }
                }   
            }
        }
        return gates;
    }

    public static ArrayList<Item> readItemsToCollect(String mapName, String mapsPath, ArrayList<Item> itemsInMap) {
        ArrayList<Item> itemsToCollect = new ArrayList<>();


        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!itemsToCollect:")) {
                        String[] itemStrings = argument.split(":")[1].split(";");

                        for(String item : itemStrings) {
                            item = item.replace("(", "");
                            item = item.replace(")", "");

                            String[] itemArguments = item.split(",");


                            if(itemArguments[0].equals("key")) {
                                if(itemArguments.length == 3) {
                                    itemsToCollect.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        true
                                    ));
                                } else if (itemArguments.length == 4) {
                                    itemsToCollect.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        true
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("energyDrink")) {
                                if(itemArguments.length == 3) {
                                    itemsToCollect.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        true
                                    ));
                                } else if (itemArguments.length == 4) {
                                    itemsToCollect.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        itemArguments[3],
                                        true
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("coat")) {
                                if(itemArguments.length == 3) {
                                    itemsToCollect.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        true
                                    ));
                                } else if (itemArguments.length == 4) {
                                    itemsToCollect.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        true
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("lock")) {
                                if(itemArguments.length == 3) {
                                    itemsToCollect.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]),
                                        true
                                    ));
                                } else if (itemArguments.length == 4) {
                                    itemsToCollect.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3],
                                        true
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } 

                        }
                    }
                }   
            }
        }
        return itemsToCollect;
    }

    public static boolean readIsLastLevel(String mapName, String mapsPath) {
        boolean isLastLevel = false;
        for(String map : MapReader.getMaps(mapsPath)) {
            if(map.startsWith(String.format("!map:%s&", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!isLastLevel")) {
                        isLastLevel = true;
                    }
                }   
            }
        }
        return isLastLevel;
    }

    public static ArrayList<String> readMapNames(String mapsPathString) {
        ArrayList<String> mapNames = new ArrayList<>();
        Path mapsPath = Paths.get(mapsPathString);
        Path absoluteMapsPath = mapsPath.toAbsolutePath();
    
        if (Files.exists(absoluteMapsPath)) {
            try {
                String data = Files.readString(absoluteMapsPath);
                data = data.replaceAll("\\s", ""); // Remove all whitespaces

                for(String line : data.split("&")) {
                    if(line.startsWith("!map:")) {
                        mapNames.add(line.split(":")[1]);
                    }

                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in obstacles data!");
            }
        } else {
            System.err.println(String.format("File: '%s' does not exist!", absoluteMapsPath.toString()));
        }
        return mapNames;
    }

    public static String getNextLevel(String levelBefore, String mapsPath) {
        if (levelBefore == null) return null;

        List<String> mapNames = MapReader.readMapNames(mapsPath);
        int index = mapNames.indexOf(levelBefore);

        if (index != -1 && index < mapNames.size() - 1) {
            return mapNames.get(index + 1);
        }

        return null;
    }
}