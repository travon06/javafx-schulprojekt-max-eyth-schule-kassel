package utils.mapConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import items.Key;
import java.util.ArrayList;

import goal.Finish;
import language.Texts;
import items.Coat;
import items.EnergyDrink;
import items.Item;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.Waypoint;
import utils.config.ConfigArguments;

public class MapReader {

    public final static ArrayList<String> MAPS = MapReader.getMaps();
    public final static ArrayList<String> MAPNAMES = MapReader.readMapNames();

    public static ArrayList<Tile> readTiles(String mapName) {
        ArrayList<Tile> tiles = new ArrayList<>(); 
    
        for (String map : MapReader.MAPS) {
            if (map.startsWith(String.format("!map:%s", mapName))) {
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
    
                            if (obstacleArguments.length == 5) { // Ensure 4 arguments
                                tiles.add(new Tile(
                                    true,
                                    Integer.parseInt(obstacleArguments[0]), // x
                                    Integer.parseInt(obstacleArguments[1]), // y
                                    Integer.parseInt(obstacleArguments[2]), // width
                                    Integer.parseInt(obstacleArguments[3]),  // height
                                    obstacleArguments[4] // image
                                ));
                            } else if (obstacleArguments.length == 4){
                                tiles.add(new Tile(
                                    true,
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
        return tiles;
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

    public static ArrayList<Policeman> readPolicemen(String mapName) {
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


                            if(itemArguments[0].equals("key")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2])
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Key(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3]
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("energyDrink")) {
                                if(itemArguments.length == 3) {
                                    items.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2])
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new EnergyDrink(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3]
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("coat")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2])
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3]
                                    ));
                                } else {
                                    throw new Error("Ilegal Item format");
                                }
                            } else if(itemArguments[0].equals("lock")) {
                                if(itemArguments.length == 3) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2])
                                    ));
                                } else if (itemArguments.length == 4) {
                                    items.add(new Coat(
                                        Texts.getTextByName(itemArguments[0]).getTextInLanguage(), 
                                        Integer.parseInt(itemArguments[1]), 
                                        Integer.parseInt(itemArguments[2]), 
                                        itemArguments[3]
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

    public static ArrayList<Waypoint> readWaypoints(String mapName, int policemanIndex) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();


        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
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

    public static Finish readFinish(String mapName) {
        Finish finish = null;

        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
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

    public static ArrayList<Item> readItemsToCollect(String mapName, ArrayList<Item> itemsInMap) {
        ArrayList<Item> itemsToCollect = new ArrayList<>();


        for(String map : MapReader.MAPS) {
            if(map.startsWith(String.format("!map:%s", mapName))) {
                String[] arguments = map.split("&");

                for(String argument : arguments) {
                    if(argument.startsWith("!itemsToCollect:")) {
                        String[] itemsToCollectNames = argument.split(":")[1].split(";");

                        for(Item item : itemsInMap) {
                            for(String itemToCollectName : itemsToCollectNames) {
                                if(item.getName().equals(Texts.getTextByName(itemToCollectName).getTextInLanguage())) {
                                    itemsToCollect.add(item);
                                }
                            }
                         }

                       
                    }
                }   
            }
        }
        return itemsToCollect;
    }

    public static ArrayList<String> readMapNames() {
        ArrayList<String> mapNames = new ArrayList<>();
        Path mapsPath = Paths.get("src/utils/mapConfig/maps.txt");
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
}