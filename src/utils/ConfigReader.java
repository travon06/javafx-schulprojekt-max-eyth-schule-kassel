package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigReader {

    public static void readConfig() {
        // Define the relative path using the Path class
        Path configPath = Paths.get("src/utils/config.txt");

        // Convert to absolute path if required
        Path absoluteConfigPath = configPath.toAbsolutePath();
        System.out.println("Config file absolute path: " + absoluteConfigPath);

        // Check if the file exists
        if (Files.exists(absoluteConfigPath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(absoluteConfigPath.toString()))) {
                String line;
                // Read the config file line by line
                while ((line = reader.readLine()) != null) {
                    ConfigArguments.getConfigArguments().add(new ConfigArgument(getArgument(line), getValue(line)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Config file does not exist: " + absoluteConfigPath);
        }
    }

    private static String getArgument(String line) {
        return line.split("=")[0].trim();  // Trim to remove any surrounding whitespace
    }

    private static String getValue(String line) {
        return line.split("=")[1].trim();  // Trim to remove any surrounding whitespace
    }

    // Optionally, add a main method for testing
    // public static void main(String[] args) {
    //     readConfig();
    //     for (ConfigArgument configArgument : ConfigArguments.getConfigArguments()) {
    //         System.out.println(String.format("%s=%s", configArgument.getArgument(), configArgument.getValue()));
    //     }
    // }
}
