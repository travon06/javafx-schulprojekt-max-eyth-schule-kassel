package utils.keyboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeybindingReader {
    public static void readKeybindings() {
        Path mapsPath = Paths.get("src/utils/keyboard/keybindings.txt");
        Path absoluteMapsPath = mapsPath.toAbsolutePath();

        try {
            String data = Files.readString(absoluteMapsPath);
            data = data.replaceAll("\\s", "");

            String[] lines = data.split(";");

            for(String line : lines) {
               String argument = getArgument(line);
               String value = getValue(line);

               Keybindings.getKeybindings().add(new Keybinding(argument, value));   
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
