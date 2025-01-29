package graphics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GraphicReader {
    public static void readGraphics() {
        String standartUrl = "./src/graphics/sprites/";
        Path mapsPath = Paths.get("src/graphics/graphics.txt");
        Path absoluteMapsPath = mapsPath.toAbsolutePath();

        try {
            String data = Files.readString(absoluteMapsPath);
            data = data.replaceAll("\\s", "");

            String[] lines = data.split(";"); 

            for(String line : lines) {
               String name = getArgument(line);
               String url = getValue(line);

               Graphics.getGraphics().add(new Graphic(name, String.format("file:%s%s", standartUrl, url)));   
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getArgument(String line) {
        return line.replace(" ", "").split("=")[0];
    }

    private static String getValue(String line) {
        return line.replace(" ", "").split("=")[1];
    }
}