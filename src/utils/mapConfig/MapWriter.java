package utils.mapConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import goal.Finish;
import graphics.GraphicReader;
import items.EnergyDrink;
import items.Item;
import items.Key;
import models.Gate;
import models.entities.Policeman;
import models.tiles.Tile;
import utils.Waypoint;
import utils.config.ConfigReader;

public class MapWriter {
    private final static String MAPSPATH = "src/utils/mapConfig/myMaps.txt";
    private final static ArrayList<String> MAPNAMES = MapReader.readMapNames(MAPSPATH);
    private String tileString;
    private String itemString;
    private String playerStartCoordinatesString;
    private String mapName;
    private String finishString;
    private String mapNameString;
    private String timeToSurviveString;
    private String policemenString;
    private String policemenWaypointString;
    private String itemsToCollectString;
    private String gatesString;


    public MapWriter(String mapName) {
        this.mapName = mapName;
        this.mapNameString = "";
        this.tileString = "";
        this.itemString = "";
        this.playerStartCoordinatesString = "";
        this.finishString = "";
        this.timeToSurviveString = "";
        this.policemenString = "";
        this.policemenWaypointString = "";
        this.itemsToCollectString = "";
        this.gatesString = "";

    }

    public void writeTiles(ArrayList<Tile> tiles) {
        if(tiles.isEmpty()) return;
        this.tileString = "!tiles:\n\tborder;\n";
        for(int i = 0; i < tiles.size(); i++) {
            this.tileString += String.format("\t(%s,%s,%s,%s,%s,%s)",
                tiles.get(i).getIsSolid(),
                tiles.get(i).getX(),
                tiles.get(i).getY(),
                tiles.get(i).getWidth(),
                tiles.get(i).getHeight(),
                tiles.get(i).getImageName()
            );
            
            if(i < tiles.size() - 1) {
                this.tileString += ";\n";
            }

        }
        this.tileString += "&\n";
    }
    
    public void writeItems(ArrayList<Item> items) {
        if(items.isEmpty()) return;

        this.itemString = "!items:\n";
        for(int i = 0; i < items.size(); i++) {
            this.itemString += String.format("\t(%s,%s,%s,%s)",
                items.get(i).getOriginalName(),
                items.get(i).getX(),
                items.get(i).getY(),
                items.get(i).getImageName()
            );
            
            if(i < items.size() - 1) {
                this.itemString += ";\n";
            }

        }
        this.itemString += "&\n";
    }
    public void writeItemsToCollect(ArrayList<Item> itemsToCollect, Finish finish) {
        if(itemsToCollect.isEmpty() || !finish.getGoal().equals("COLLECT_ITEMS")) return;

        this.itemsToCollectString = "!itemsToCollect:\n";
        for(int i = 0; i < itemsToCollect.size(); i++) {
            this.itemsToCollectString += String.format("\t(%s,%s,%s,%s)",
                itemsToCollect.get(i).getOriginalName(),
                itemsToCollect.get(i).getX(),
                itemsToCollect.get(i).getY(),
                itemsToCollect.get(i).getImageName()
            );
            
            if(i < itemsToCollect.size() - 1) {
                this.itemsToCollectString += ";\n";
            }

        }
        this.itemsToCollectString += "&\n";
    }

    public void writePlayerStartCoordinates(int[] playerStartCoordinates) {
        this.playerStartCoordinatesString = String.format("!playerStartCoordinates:%s,%s&\n", 
            playerStartCoordinates[0],
            playerStartCoordinates[1]
        );
    }

    public void writeFinish(Finish finish) {
        this.finishString = String.format("!finish:(%d,%d,%s)&\n",
            finish.getX(),
            finish.getY(),
            finish.getGoal()
        );
    }

    public void writeMapName() {
        this.mapNameString = String.format("!map:%s&\n", this.mapName);
    }

    public void writeTimeToSurvive(int timeToSurvive, Finish finish) {
        if(timeToSurvive == 0 || !finish.getGoal().equals("SURVIVE")) return;
        this.timeToSurviveString = String.format("!timeToSurvive:%d&\n", timeToSurvive);
    }

    public void writePoilicemen(ArrayList<Policeman> policemen) {
        if(policemen.isEmpty()) return;

        this.policemenString = "!policemen:\n";

        for(int i = 0; i < policemen.size(); i++) {
            this.policemenString += String.format("\t(%.0f,%.0f,%.0f)", 
                policemen.get(i).getX(),
                policemen.get(i).getY(),
                policemen.get(i).getSpeed()
            );

            if(i < policemen.size() - 1) {
                policemenString += ";\n";
            }
        }

        policemenString += "&\n";
    }

    public void writePolicemenWaypoints(ArrayList<Policeman> policemen) {
        if(policemen.isEmpty()) return;

        this.policemenWaypointString = "!policemenWaypoints:{\n";

        for(int i = 0; i < policemen.size(); i++) {
            ArrayList<Waypoint> policemanWaypoints = policemen.get(i).getWaypoints();
            this.policemenWaypointString += "\t[";
            for(int j = 0; j < policemanWaypoints.size(); j++) {
                String waypointString = String.format("(%d,%d,true)",
                    policemanWaypoints.get(j).getX(),
                    policemanWaypoints.get(j).getY()
                );

                if(j < policemanWaypoints.size() - 1) {
                    waypointString += ";";
                }

                this.policemenWaypointString += waypointString;
            }

            this.policemenWaypointString += "]\n";
        }

        this.policemenWaypointString += "}&\n";
    }

    public void writeGates(ArrayList<Gate> gates) {
        this.gatesString = "!gates:\n";
        for(int i = 0; i < gates.size(); i++) {
            this.gatesString += String.format("\t%d,%d", gates.get(i).getX(), gates.get(i).getY());

            if(i < gates.size() - 1) {
                this.gatesString += ";\n";
            }
        }
        this.gatesString += "&\n";
    }

    // !policemenWaypoints:{
    //     [(201,474);(1000,474)]
    //     [(724,77);(980,77)]
    //     [(66,225);(1170,225)]
    // }&

    public void createMap(
        int[] playerStartCoordinates, 
        ArrayList<Tile> tiles, 
        ArrayList<Item> items, 
        ArrayList<Policeman> policemen,
        ArrayList<Gate> gates, 
        Finish finish, 
        ArrayList<Item> itemsToCollect, 
        int timeToSurvive
    ) {
        writeMapName();
        writePlayerStartCoordinates(playerStartCoordinates);
        writeTiles(tiles);
        writeItems(items);
        writePoilicemen(policemen);
        writePolicemenWaypoints(policemen);
        writeGates(gates);
        writeFinish(finish);
        writeItemsToCollect(itemsToCollect, finish);
        writeTimeToSurvive(timeToSurvive, finish);
        

        String map = String.format("%s%s%s%s%s%s%s%s%s%s%s", 
            this.mapNameString,
            this.playerStartCoordinatesString,
            this.tileString, 
            this.itemString, 
            this.policemenString,
            this.policemenWaypointString,
            this.gatesString,
            this.finishString,
            this.itemsToCollectString,
            this.timeToSurviveString,
            "!mapEnd&\n"
        );

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(MAPSPATH, true))) {
            writer.write(map);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
